/*
 * Name:   Double Array Trie
 * Author: Yaguang Ding
 * Date:   2012/5/21
 * Note: a word ends may be either of these two case:
 * 1. Base[cur_p] == pos  ( pos<0 and Tail[-pos] == 'END_CHAR' )
 * 2. Check[Base[cur_p] + Code('END_CHAR')] ==  cur_p
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;


public class DoubleArrayTrie2012_5_28 {
	final char END_CHAR = '\0';
	final int DEFAULT_LEN = 1024;
	int Base[]  = new int [DEFAULT_LEN];
	int Check[] = new int [DEFAULT_LEN];
	char Tail[] = new char [DEFAULT_LEN];
	int Pos = 1;
	Map<Character ,Integer> CharMap = new HashMap<Character,Integer>();
	ArrayList<Character> CharList = new ArrayList<Character>();
	
	public DoubleArrayTrie2012_5_28()
	{
		Base[1] = 1;
		
		CharMap.put(END_CHAR,1);
		for(int i=0;i<26;++i)
		{
			CharMap.put((char)('a'+i),CharMap.size()+1);
		}
		
	}
	
	public void Debug(int len)
	{
		for(int i=0; i<len;++i)
		{
			System.out.printf("%d\t",i+1);
		}
		System.out.print("\n");
		
		for(int i=0; i<len;++i)
		{
			System.out.printf("%d\t",this.Base[i+1]);
		}
		System.out.print("\n");
		
		for(int i=0; i<len;++i)
		{
			System.out.printf("%d\t",this.Check[i+1]);
		}
		System.out.print("\n");
		
		for(int i=0; i<len;++i)
		{
			System.out.printf("%c\t",this.Tail[i+1]);
		}
		System.out.print("\n");
		System.out.printf("Pos=%d",Pos);
		System.out.print("\n\n");
	}
	
	private void Extend_Array()
	{
		Base = Arrays.copyOf(Base, Base.length*2);
		Check = Arrays.copyOf(Check, Check.length*2);
	}
	
	private void Extend_Tail()
	{
		Tail = Arrays.copyOf(Tail, Tail.length*2);
	}
	
	private int GetCharCode(char c)
	{
		if (!CharMap.containsKey(c))
		{
			CharMap.put(c,CharMap.size()+1);
			CharList.add(c);
		}
		return CharMap.get(c);
	}
	private int CopyToTailArray(String s,int p)
	{
		int _Pos = Pos;
		while(s.length()-p+1 > Tail.length-Pos)
		{
			Extend_Tail();
		}
		for(int i=p; i<s.length();++i)
		{
			Tail[_Pos] = s.charAt(i);
			_Pos++;
		}
		//结束标识
		//Tail[_Pos] = 0;
		//_Pos++;
		return _Pos;
	}
	private int fabs(int a)
	{
		return a<0?-a:a;
	}
	
	private int x_check(Integer []set)
	{
		for(int i=1; ; ++i)
		{
			boolean flag = true;
			for(int j=0;j<set.length;++j)
			{
				int cur_p = i+set[j];
				if(cur_p>= Base.length) Extend_Array();
				if(Base[cur_p]!= 0 || Check[cur_p]!= 0)
				{
					flag = false;
					break;
				}
			}
			if (flag) return i;
		}
	}
	
	private ArrayList<Integer> GetChildList(int p)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int i=1; i<=CharMap.size();++i)
		{
			// BUG
			if(Base[p]+i >= Check.length) break;
			if(Check[Base[p]+i] == p)
			{
				ret.add(i);
			}
		}
		return ret;
	}
	
	private boolean TailContainString(int start,String s2)
	{
		for(int i=0;i<s2.length();++i)
		{
			if(s2.charAt(i) != Tail[i+start]) return false;
		}
		
		return true;
	}
	//private int 
	
	public void Insert(String s) throws Exception
	{
		s += END_CHAR;
		
		//System.out.println(s);
		int pre_p = 1;
		int cur_p;
		if(s.equals("village#"))
		{
			int debug =1;
		}
		if(s.equals("Rufe#"))
		{
			int debug =1;
		}
		for(int i=0; i<s.length(); ++i)
		{
			//获取状态位置
			cur_p = Base[pre_p]+GetCharCode(s.charAt(i));
			//如果长度超过现有，拓展数组
			if (cur_p >= Base.length) Extend_Array();
			
			//空闲状态
			if(Base[cur_p] == 0 && Check[cur_p] == 0)
			{
				Base[cur_p] = -Pos;
				Check[cur_p] = pre_p;
				Pos = CopyToTailArray(s,i+1);
				break;
			}else
			//已存在状态
			if(Base[cur_p] > 0 && Check[cur_p] == pre_p)
			{
				pre_p = cur_p;
				continue;
			}else
			//冲突1
			if(Base[cur_p] < 0 && Check[cur_p] == pre_p)
			{
				int head = -Base[cur_p];
				
				if(s.charAt(i+1)== END_CHAR && Tail[head]==END_CHAR)	//插入重复字符串
				{
					break;
				}
				
				//公共字母的情况，因为上一个判断已经排除了结束符，所以一定是2个都不是结束符
				if (Tail[head] == s.charAt(i+1))
				{
					int avail_base = x_check(new Integer[]{GetCharCode(s.charAt(i+1))});
					Base[cur_p] = avail_base;
					
					Check[avail_base+GetCharCode(s.charAt(i+1))] = cur_p;
					
					if(Tail[head] == END_CHAR)
					{
						Base[avail_base+GetCharCode(s.charAt(i+1))] = 0;
						throw new Exception("2");
					}
					else
						Base[avail_base+GetCharCode(s.charAt(i+1))] = -(head+1);
					pre_p = cur_p;
					continue;
				}
				else
				{
					//2个字母不相同的情况，可能有一个为结束符
					int avail_base ;
					//if (Tail[head] == 0)
					//	avail_base = x_check(new Integer[]{GetCharCode(s.charAt(i+1))});
					//else
					avail_base = x_check(new Integer[]{GetCharCode(s.charAt(i+1)),GetCharCode(Tail[head])});
					
					Base[cur_p] = avail_base;
					
					Check[avail_base+GetCharCode(Tail[head])] = cur_p;
					Check[avail_base+GetCharCode(s.charAt(i+1))] = cur_p;
					
					//Tail 为END_FLAG 的情况
					if(Tail[head] == END_CHAR)
						Base[avail_base+GetCharCode(Tail[head])] = 0;
					else
						Base[avail_base+GetCharCode(Tail[head])] = -(head+1);
					if(s.charAt(i+1) == END_CHAR) 
						Base[avail_base+GetCharCode(s.charAt(i+1))] = 0;
					else
						Base[avail_base+GetCharCode(s.charAt(i+1))] = -Pos;
					
					Pos = CopyToTailArray(s,i+2);
					break;
				}
			}else
			//冲突2
			if(Check[cur_p] != pre_p)
			{
				ArrayList<Integer> list1 = GetChildList(pre_p);
				//System.out.println(cur_p);
				ArrayList<Integer> list2 = GetChildList(Check[cur_p]);
				int AdjustPreORCheckCur = 1;	//调整的是pre的base还是check的base
				
				int toBeAdjust;
				ArrayList<Integer> list = null;
				if(list1.size()+1 < list2.size() || true)
				{
					toBeAdjust = pre_p;
					list = list1;
				}else
				{
					toBeAdjust = Check[cur_p];
					list = list2;
					AdjustPreORCheckCur = 2;
				}
				
				int origin_base = Base[toBeAdjust];
				list.add(GetCharCode(s.charAt(i)));
				int avail_base = x_check((Integer[])list.toArray(new Integer[list.size()]));
				list.remove(list.size()-1);
				
				Base[toBeAdjust] = avail_base;
				for(int j=0; j<list.size(); ++j)
				{
					//BUG 
					int tmp1 = origin_base + list.get(j);
					int tmp2 = avail_base + list.get(j);
					
					Base[tmp2] = Base[tmp1];
					Check[tmp2] = Check[tmp1];
					
					//有后续
					if(Base[tmp1] > 0)
					{
						ArrayList<Integer> subsequence = GetChildList(tmp1);
						for(int k=0; k<subsequence.size(); ++k)
						{
							Check[Base[tmp1]+subsequence.get(k)] = tmp2;
						}
					}
					
					Base[tmp1] = 0;
					Check[tmp1] = 0;
				}
				
				// BUG 此时的pre_p已经有可能已经变了，所以不能引用pre_p
				if (AdjustPreORCheckCur == 1)
				{
					//调整的是Pre的Base
					cur_p = Base[pre_p]+GetCharCode(s.charAt(i));
				}
				else if (AdjustPreORCheckCur == 2)
				{
					//调整的是Check[cur]的Base
					//pre_p = Check[cur_p];
				}
				//cur_p = Base[pre_p]+GetCharCode(s.charAt(i));
				if (Base[cur_p]!=0 || Check[cur_p]!=0) throw new Exception("3");
				//if(cur_p >= Base.length) Extend_Array();
				if(s.charAt(i) == END_CHAR)
					Base[cur_p] = 0;
				else
					Base[cur_p] = -Pos;
				Check[cur_p] = pre_p;
				if(Check[cur_p] ==0) throw new Exception("4");
				Pos = CopyToTailArray(s,i+1);
				break;
			}
		}
	}
	
	public int Exists(String word)
	{
		int pre_p = 1;
		int cur_p = 0;
		
		for(int i=0;i<word.length();++i)
		{
			cur_p = Base[pre_p]+GetCharCode(word.charAt(i));
			if(Check[cur_p] != pre_p) return -1;
			if(Base[cur_p] < 0)
			{
				if(TailContainString(-Base[cur_p],word.substring(i+1)))
					return cur_p;
				return -1;
			}
			if(Check[Base[cur_p]+GetCharCode(END_CHAR)] == cur_p)
				return cur_p;
			pre_p = cur_p;
		}
		return cur_p;
	}
	
	public int Find(String word)
	{
		int pre_p = 1;
		int cur_p = 0;
		
		for(int i=0;i<word.length();++i)
		{
			// BUG
			cur_p = fabs(Base[pre_p])+GetCharCode(word.charAt(i));
			if(Check[cur_p] != pre_p) return -1;
			if(Base[cur_p] < 0)
			{
				if(TailContainString(-Base[cur_p],word.substring(i+1)))
					return cur_p;
				return -1;
			}
			pre_p = cur_p;
		}
		return cur_p;
	}
	public String FindAllWords(String word)
	{
		//ArrayList<String> result = new ArrayList<String>();
		int p = Find(word);
		if (p == -1) return "null";
		if(Base[p]<0)
		{
			String r="";
			for(int i=-Base[p];Tail[i]!=END_CHAR;++i)
			{
				r+= Tail[i];
			}
			return r;
		}
		
		return "too many";
	}
	
}
