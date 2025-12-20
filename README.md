DoubleArrayTrie
===============

DoubleArrayTrie Java Implementation

Introduction
----
[https://www.co-ding.com/blog/algorithm/2013-05-28-DoubleArrayTrie.html
](https://www.co-ding.com/blog/algorithm/2013-05-28-DoubleArrayTrie.html)


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
