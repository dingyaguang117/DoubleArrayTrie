DoubleArrayTrie
===============

DoubleArrayTrie by java

Introduce
----
[http://www.co-ding.com/2012/05/28/DoubleArrayTrie](http://www.co-ding.com/2012/05/28/DoubleArrayTrie)

Usage
----


```
// construct and build
DoubleArrayTrie dat = new DoubleArrayTrie();  

for(String word: words)  
{  
    dat.Insert(word);  
}  


System.out.println(dat.Base.length);  
System.out.println(dat.Tail.length);  

String word = sc.next();  

// Look up a word
System.out.println(dat.Exists(word));  

// Find all words that begin with a string
System.out.println(dat.FindAllWords(word));  
```
