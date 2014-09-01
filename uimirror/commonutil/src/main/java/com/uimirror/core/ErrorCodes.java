/*******************************************************************************
 * Copyright (c) 2014 Uimirror.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Uimirror license
 * which accompanies this distribution, and is available at
 * http://www.uimirror.com/legal
 *
 * Contributors:
 * Uimirror Team
 *******************************************************************************/
package com.uimirror.core;

/**
 * <p>This has all the constant codes of a web code</p>
 * @author Jay
 */
public interface ErrorCodes {
	
	//1xx series
	/**
	 * Continue
	 * This means that the server has received the request headers, and that the client should proceed to send the request body 
	 * (in the case of a request for which a body needs to be sent; for example, a POST request)
	 */
	public static final int _100 = 100;
	
	/**
	 * Switching Protocols
	 * This means the requester has asked the server to switch protocols and the server is acknowledging that it will do so.
	 */
	public static final int _101 = 101;
	
	/**
	 * Processing (WebDAV; RFC 2518)
	 * As a WebDAV request may contain many sub-requests involving file operations, it may take a long time to complete the request. This code indicates that the server
	 *  has received and is processing the request, but no response is available yet.[2] This prevents the client from timing out and assuming the request was lost.
	 */
	public static final int _102 = 102;
	
	//2xx series
	
	/**
	 * OK
	 * Standard response for successful HTTP requests.
	 */
	public static final int _200 = 200;
	
	/**
	 * Created
	 * The request has been fulfilled and resulted in a new resource being created.
	 */
	public static final int _201 = 201;
	
	/**
	 * Accepted
	 * The request has been accepted for processing, but the processing has not been completed. The request might or might not eventually be acted upon, 
	 * as it might be disallowed when processing actually takes place.
	 */
	public static final int _202 = 202;
	
	/**
	 * Non-Authoritative Information (since HTTP/1.1)
	 * The server successfully processed the request, but is returning information that may be from another source.
	 */
	public static final int _203 = 203;
	
	/**
	 * No Content
	 */
	public static final int _204 = 204;
	
	/**
	 * Reset Content
	 */
	public static final int _205 = 205;
	
	/**
	 * Partial Content
	 * The server is delivering only part of the resource (byte serving) due to a range header sent by the client. 
	 */
	public static final int _206 = 206;
	
	/**
	 * Multi-Status (WebDAV; RFC 4918)
	 * The message body that follows is an XML message and can contain a number of separate response codes, depending on how many sub-requests were made.
	 */
	public static final int _207 = 207;
	
	/**
	 * Already Reported (WebDAV; RFC 5842)
	 * The members of a DAV binding have already been enumerated in a previous reply to this request, and are not being included again.
	 */
	public static final int _208 = 208;
	
	/**
	 * IM Used (RFC 3229) The server has fulfilled a GET request for the resource, and the response is a representation of the result of one or more 
	 * instance-manipulations applied to the current instance.
	 */
	public static final int _226 = 226;
	
	//3xx series
	
	/**
	 * Multiple Choices
	 * Indicates multiple options for the resource that the client may follow. It, for instance, could be used to present different format options for video, list files with different 
	 * extensions,	or word sense disambiguation.
	 */
	public static final int _300 = 300;
	
	/**
	 *  Moved Permanently
	 */
	public static final int _301 = 301;
	
	/**
	 * Found
	 */
	public static final int _302 = 302;
	
	/**
	 *  See Other (since HTTP/1.1)
	 * The response to the request can be found under another URI using a GET method. When received in response to a POST (or PUT/DELETE), it should be assumed that the server has 
	 * received the data and the redirect should be issued with a separate GET message.
	 */
	public static final int _303 = 303;
	
	/**
	 * Not Modified
	 * Indicates that the resource has not been modified since the version specified by the request headers If-Modified-Since or If-Match. This means that there is no need to
	 *  retransmit the resource, since the client still has a previously-downloaded copy.
	 */
	public static final int _304 = 304;
	
	/**
	 * Use Proxy (since HTTP/1.1)
	 * The requested resource is only available through a proxy, whose address is provided in the response.
	 */
	public static final int _305 = 305;
	
	/**
	 * Switch Proxy
	 * No longer used. Originally meant "Subsequent requests should use the specified proxy."
	 */
	public static final int _306 = 306;
	
	/**
	 * Temporary Redirect (since HTTP/1.1)
	 * In this case, the request should be repeated with another URI; however, future requests should still use the original URI. 
	 * In contrast to how 302 was historically implemented, the request method is not allowed to be changed when reissuing the original request. 
	 * 
	 */
	public static final int _307 = 307;
	
	/**
	 * Permanent Redirect (Experimental RFC; RFC 7238)
	 * The request, and all future requests should be repeated using another URI. 307 and 308 (as proposed) parallel the behaviours of 302 and 301, but do not allow the HTTP 
	 * method to change. 
	 */
	public static final int _308 = 308;
	//4xx series
	
	/**
	 * Bad Request
	 */
	public static final int _400 = 400;
	
	/**
	 * Unauthorized
	 */
	public static final int _401 = 401;
	
	/**
	 * Payment Required
	 */
	public static final int _402 = 402;
	
    /**
     * Forbidden
     */
	public static final int _403 = 403;
	
	/**
	 * Not Found
	 */
	public static final int _404 =  404;
	
	/**
	 * Method Not Allowed A request was made of a resource using a request method not supported by that resource;
	 */
	public static final int _405 = 405;
	
	/**
	 * Not Acceptable
	 * The requested resource is only capable of generating content not acceptable according to the Accept headers sent in the request.
	 */
	public static final int _406 = 406;
	
	/**
	 * Proxy Authentication Required
	 */
	public static final int _407 = 407;
	
	/**
	 * Request Timeout
	 */
	public static final int _408 = 408;
	
	/**
	 * Conflict
	 *  Indicates that the request could not be processed because of conflict in the request
	 */
	public static final int _409 = 409;
	
	/**
	 * Gone
	 * Indicates that the resource requested is no longer available and will not be available again. This should be used when a resource has been intentionally removed 
	 * and the resource should be purged. Upon receiving a 410 status code, the client should not request the resource again in the future. Clients such as search engines 
	 * should remove the resource from their indices.
	 */
	public static final int _410 = 410;
	
	/**
	 * Length Required
	 * The request did not specify the length of its content, which is required by the requested resource.
	 */
	public static final int _411 = 411;
	
	/**
	 * Precondition Failed
	 */
	public static final int _412 = 412;
	
	/**
	 * Request Entity Too Large
	 * The request is larger than the server is willing or able to process.
	 */
	public static final int _413 = 413;
	
	/**
	 * Request-URI Too Long
	 * The URI provided was too long for the server to process. 
	 */
	public static final int _414 = 414;
	
	/**
	 * Unsupported Media Type
	 * The request entity has a media type which the server or resource does not support.
	 */
	public static final int _415 = 415;
	
	/**
	 * Requested Range Not Satisfiable
	 * The client has asked for a portion of the file (byte serving), but the server cannot supply that portion. 
	 */
	public static final int _416 = 416;
	
	/**
	 * Expectation Failed
	 * The server cannot meet the requirements of the Expect request-header field.
	 */
	public static final int _417 = 417;
	
	/**
	 * I'm a teapot (RFC 2324)
	 * This code was defined in 1998 as one of the traditional IETF April Fools' jokes, in RFC 2324, Hyper Text Coffee Pot Control Protocol, and is not expected to be 
	 * implemented by actual HTTP servers.
	 */
	public static final int _418 = 418;
	
	/**
	 * Authentication Timeout
	 */
	public static final int _419 = 419;
	
	/**
	 * Method Failure (Spring Framework)Not part of the HTTP standard, but defined by Spring in the HttpStatus class to be used when a method failed.
	 *  This status code is deprecated by Spring.
	 *                 &
	 * Enhance Your Calm (Twitter)
	 * Not part of the HTTP standard, but returned by version 1 of the Twitter Search and Trends API when the client is being rate limited.[12] Other services may wish to 
	 * implement the 429 Too Many Requests response code instead.
	 */
   	public static final int _420 = 420;
	
	//5xx series
	
	/**
	 * Internal Server Error
	 */
	public static final int _500 = 500;
}
