DoubleArrayTrie
===============

DoubleArrayTrie by java

usage
----


DoubleArrayTrie dat = new DoubleArrayTrie();  
  
for(String word: words)  
{  
    dat.Insert(word);  
}  
  
System.out.println(dat.Base.length);  
System.out.println(dat.Tail.length);  

String word = sc.next();  
System.out.println(dat.Exists(word));  
System.out.println(dat.FindAllWords(word));  

