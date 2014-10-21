package com.uimirror.account.auth.user.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.account.auth.controller.AccessTokenProvider;
import com.uimirror.account.auth.core.TokenGenerator;
import com.uimirror.account.auth.core.processor.InvalidateTokenProcessor;
import com.uimirror.account.user.dao.UserCredentialsStore;
import com.uimirror.account.user.form.VerifyForm;
import com.uimirror.account.user.form.VerifySource;
import com.uimirror.core.Processor;
import com.uimirror.core.auth.AccessToken;
import com.uimirror.core.auth.AuthConstants;
import com.uimirror.core.auth.Scope;
import com.uimirror.core.auth.Token;
import com.uimirror.core.auth.TokenType;
import com.uimirror.core.auth.token.DefaultAccessToken;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.core.util.DateTimeUtil;
import com.uimirror.core.util.thread.BackgroundProcessorFactory;
import com.uimirror.ws.api.security.exception.InvalidTokenException;

public class VerifyActivateUserAccountProcessor implements Processor<VerifyForm, String> {

  @Autowired
  private AccessTokenProvider persistedAccessTokenProvider;
  @Autowired
  private BackgroundProcessorFactory<String, Object> backgroundProcessorFactory;
  @Autowired
  private ResponseTransFormer<String> jsonResponseTransFormer;

  @Autowired
  private UserCredentialsStore userCredentialStore;

  @Override
  public String invoke(VerifyForm param) throws ApplicationException {
    
    param.isValid();
    VerifySource accountActivationSource = param.getSource();
    String codeFromForm = param.getCode();
    String prevToken = param.getToken();
    AccessToken newAccessToken = null;
    AccessToken prevAccessToken = null;
    if (VerifySource.MAIL == accountActivationSource) {
      prevAccessToken = persistedAccessTokenProvider.get(prevToken);
      if (prevAccessToken == null) {
        throw new InvalidTokenException();
      }

    } else {
      prevAccessToken = persistedAccessTokenProvider.getValid(prevToken);
      if (prevAccessToken == null) {
        throw new InvalidTokenException();
      }

      Map<String, Object> instructionsMap = prevAccessToken.getInstructions();
      String codeFromAccessToken = (String) instructionsMap.get(codeFromForm);
      if (codeFromAccessToken == null || codeFromAccessToken.length() == 0) {
        throw new IllegalArgumentException("Invalid code");
      }

    }

    userCredentialStore.enableAccount(prevAccessToken.getOwner());
    newAccessToken = getNewToken(prevAccessToken);

    persistedAccessTokenProvider.store(newAccessToken);
    // Invalidate the previous Token
    backgroundProcessorFactory.getProcessor(InvalidateTokenProcessor.NAME).invoke(prevToken);

    return jsonResponseTransFormer.doTransForm(newAccessToken);
  }

  /**
   * Generate a new Access Token by extending its expire.
   * 
   * @param prev_token
   * @return
   */
  private AccessToken getNewToken(AccessToken prev_token) {
    Map<String, Object> instructions = prev_token.getInstructions();
    Map<String, Object> notes = prev_token.getNotes();
    Token token = TokenGenerator.getNewOneWithOutPharse();
    TokenType type = TokenType.ACCESS;
    Scope scope = prev_token.getScope();
    String requestor = prev_token.getClient();
    String owner = prev_token.getOwner();
    // Get Expires On
    long expiresOn = getExpiresOn(instructions);
    return new DefaultAccessToken(token, owner, requestor, expiresOn, type, scope, notes, instructions);
  }

  /**
   * Decides the expires interval of the token
   * 
   * @param instructions
   * @param type
   * @return
   */
  private long getExpiresOn(Map<String, Object> instructions) {
    return DateTimeUtil.addToCurrentUTCTimeConvertToEpoch(getExpiresInterval(instructions));
  }

  /**
   * Gets the expires period of the token
   * 
   * @param instructions
   * @return
   */
  private long getExpiresInterval(Map<String, Object> instructions) {
    long expires = AuthConstants.DEFAULT_EXPIRY_INTERVAL;
    if (instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL) != null) {
      expires = (long) instructions.get(AuthConstants.INST_AUTH_EXPIRY_INTERVAL);
    } else {
      // Put back the expire period for future
      instructions.put(AuthConstants.INST_AUTH_EXPIRY_INTERVAL, expires);
    }
    return expires;
  }

}
