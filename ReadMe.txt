1. Based on the JVM heap space the maximum number of strings that can be
   handled in the input files is 25.000.000 8-character strings
   
2. I would change the JVM heap space size during the JVM launch. This can 
   be customized by specifying JVM parameters -Xmx
   
3. The program is efficient. The most time-consuming sorting algorithm used twice 
   for the lexicographically needed sorting of the two files input strings is a
   modified mergesort offering n log(n) performance.
   
4. Nothing more than already done
  