----------------------
Command Line Arguments
----------------------

Example:  ./start.sh --env='-Denv=prod' --port=8080 --nio.port=8443 --contextpath='-DcontextSource=classpath:application'  

pas OPTS to override any jvm parameters
-env | -port | -nio.port | 
  (required) Specifies which environment it will use with post and nio port 

    -env - Specifies any environment it will configure 
    -port - Specifies the port application will start 
    -nio.port - specifies the connector shutdown port 

JAVA_OPTS='-Xmx3g' to override any system properties


-------------------------
Default JVM optimizations
-------------------------
-Xmx3g" "-XX:MaxNewSize=2g" "-XX:NewSize=1g" "-XX:SurvivorRatio=16" 
"-XX:+UseParNewGC" "-XX:+UseConcMarkSweepGC" "-XX:MaxTenuringThreshold=0" 
"-XX:CMSInitiatingOccupancyFraction=60" "-XX:+CMSParallelRemarkEnabled" 
"-XX:+UseCMSInitiatingOccupancyOnly" "-XX:ParallelGCThreads=12" "-XX:LargePageSizeInBytes=256m

-----------------------
Features
-----------------------

These were the steps to the process:
    1 ) run word-count on each essay
    2 ) record the frequency for the top 28 most frequent words and
	take a subtotal for each (this will be the total number of
	'relevant' words in each literature)
    3 ) isolate the words that made it to top 7 (there were 10), and
	take their percentage with respect to the subtotals
    4 ) anaylze by comparing the percentages

Of the 10 words to anaylze, words not used at predictable frequencies by Sir
Francis Bacon (is, in, you) were ignored.  That left 6 words that are used
at predictable frequencies by Bacon (of, the, and, that, to, a), and 1 word
that is not used significantly by Bacon (I).  Two of the 6 words used
frequently and predictably by Bacon (to, a) were used just as frequent
and predictable in Hamlet and All's Well that Ends Well.  On the other hand,
the 4 remaining words (of, the, and, that), which also happens to be the words
used the most frequent by Bacon, are used at only 1/2 to 2/3 the frequency by
the author of Hamlet and All's Well that Ends Well.  The minor similarities
hardly make up for the major differeces.  Based on the above analysis, we 
conclude that Bacon wrote neither of Shakespeare's plays.
	
---------
Clean Databse
---------
run the script drop_all_db.sh present in the script folder to clean all the content from
the account db.

Example ./drop_all_db.sh --port<port_num> --host=<host_name> -u=<user_name> -p=<password>
	
	port : Specifies the port to connect to , default to 57980
	host : specifies host to connect to, default to 127.0.0.1
	u    : Specifies user name for the data base
	p    : Specifies password for the data base

---------
Known Issue
---------

Our expected performance bottlenecks for the word-count program are...
	+ the Splay operation for SplayTree
	+ the insertHelp function for AVL, and
	+ the PercolateDown operation for Heap
We chose these to be the bottlenecks because we assumed the function call
frequencies and costs. However, it turned out that some of these are not huge
bottlenecks at all. For example, Splay funtion doesn't take as much time as we
thought.  Words that are inserted frequently don't take so much rotations
because they are already close to the root.


  %   cumulative   self              self     total           
 time   seconds   seconds    calls  ns/call  ns/call  name    

  1.05      1.55     0.02                             SplayTree<>::Splay()
  4.26      0.45     0.10	                      AVLTree<>::insertHelp()
  1.28      1.76     0.03                             Heap::percDown()


The gprof results show that, in general standard I/O operations takes up most 
of the runtime:


index % time    self  children    called     name
[2]     24.3    0.05    0.52   45429         next_token()
[3]     20.0    0.05    0.42                 operator<<()


Even though each word is only read/printed once, we think the cause for I/O
as a bottleneck is its huge constant overhead (which much exceeds the log n
tree operations)

For specific choice of trees and input, namely bst tree with word.txt as
input, the biggest bottleneck was string operations and FindNode() function. 
The cause is that the pre-sorted words created an unbalanced binary search
tree, requiring a complete traversal of a linked-list-like data structure
everytime a word is inserted.  At each node visited during the traversal,
FindNode compares the string keys.


index % time    self  children    called     name
                                                 <spontaneous>
[1]     75.6  167.51  533.04                 std::string::compare()
               99.37  235.36 3408496094/3408541521     std::string::size()
               35.05   81.63 1704248047/1704293468     std::string::data() 
               81.63    0.00 1704248047/2523092351     std::string::_M_data()


Another significant operation which we overlooked (for the AVLTree) is the
UpdateHeight function (which actually took 3.8 percent of the run time).

  %   cumulative   self              self     total           
 time   seconds   seconds    calls  ns/call  ns/call  name    
  3.83      0.54     0.09                             AVLTree<>::updateHeight()


--------------------
Endpoints
--------------------

From our "SelectionSortSort vs. HeapSort" plot, we can see that when N is greater than
8000, selectionsort function grows faster than heapsort function. This confroms our knowledge
that heap sort has better performance than selecion sort in terms of time.

We know that both mergesort(f) and heapsort(g)  have O(n logn) runing time.
In our "MergeSort vs. HeapSort" plot, two lines are parallel to each other most of the time.
This shows that f/g = constant, which means that the runing time of the two algorithms are different
by a constant factor.
