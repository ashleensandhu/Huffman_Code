import java.util.*;
import java.io.*;
/**
 * This class will ask user for text file and read it then generate the huff code
 *for it.
 *
 * @author Ashleen Sandhu
 * @version 06/09/22
 */
public class TextToCode
{
   private Map<Character, Integer> map;
   private Map<Character, String> map2;
   private List<Character> list;
   private PriorityQueue<Node> q;
   
   //node class
   private class Node implements Comparable<Node>
   {
      private Character character;
      private Integer freq;
      private Node nodeLeft;
      private Node nodeRight;
     
      public Node(Character c, int f)
      {
         this.character = c;
         this.freq = f;
      }
      
      public Node()
      {
         this.character = null;
         this.freq = null;
      }
      
      public String toString()
      {
         return(this.character + this.freq.toString());
      }
      
      public int compareTo(Node other)
      {
          if (this.freq == other.freq)
         {       
            return 0;
         }
         else
         {
            return this.freq-other.freq;
         }
      }
      
      public boolean isLeaf()
      {
         if(nodeRight == null && nodeLeft ==null)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
   } 
   /** 
   *This is the main driver function called by the o.s.
   *@param args Program variables from the o.s.
   */
   public static void main(String[] args)
   {
      TextToCode tc = new TextToCode();
   }
   /** 
   *This is the default constructor that asks reads a text file and converts it to
   *it's huffman code
   */
   public TextToCode()
   {
      list = new ArrayList<>(); 
      map = new TreeMap<>();
      map2 = new TreeMap<>();
      
      //ask user for text file
      Scanner s = new Scanner(System.in);
      System.out.println("please enter a text file name with .txt: ");
      String fileName = s.nextLine();
      if(!fileName.endsWith(".txt")) //does not work
      {
         System.out.println("please enter a text file name with .txt: ");
         fileName = s.nextLine();
      }
      
      FileInputStream x = null;
      
      //open file for reading
      try
      {
         File myFile = new File(fileName);
         x = new FileInputStream(myFile);
         
         while(x.available() > 0) 
         { 
            char c = (char) x.read();
            list.add(c);
         }      
      }
      catch(Exception e)
      {
         System.out.println("File was not found");
      }     
      for(Character a : list)
      {
         if(!map.containsKey(a))
         {
            map.put(a, 1);
         }
         else
         {
            map.put(a, map.get(a)+1);
         }
      }
     //creating priority queue
       q = new PriorityQueue<>();
       
       for(Character j : map.keySet())
       {
         Node a = new Node(j, map.get(j));
         q.add(a);
       }
       
       while(q.size() >1)
       {
         Node remove1 = q.poll();
         Node remove2 = q.poll();
         
         Node newNode = new Node(null, remove1.freq +remove2.freq);
         
         newNode.nodeLeft = remove1;
         newNode.nodeRight = remove2;
         
         q.add(newNode);
       }
       //calling my methods
       recursion();
       writeCode(fileName);
       writeHuff(fileName);
       
   } 
   //recursion helper method
   private void recursion(Node x, String s)
   {      
       
       if(x == null) return;
       if(x.isLeaf())
       {
         map2.put(x.character,  s);
       }
       else
       {
         recursion(x.nodeRight, s + "0");
         recursion(x.nodeLeft, s + "1");
       }

   } 
   //recursion method
   private void recursion()
   {
      String s ="";
      recursion(q.peek(), s);
   }
   //This method creates a .code file and stores the char as an int and its huff value
   private void writeCode(String fileName)
   {
      String codeFile = fileName.substring(0, fileName.length()-4) + ".code";
      
      try
      {
         PrintStream out = new PrintStream(codeFile);//creating file
         for(Character c : map2.keySet())
         {
            out.println((int)c);//prints char as int
            out.println(map2.get(c));
         }
         out.close();
      }
      catch(Exception e)
      {
         System.out.println(e.toString());
         System.out.println("File not found");
      }
   }
   //This method creates a .huff file and stores the huff value in one long line
   private void writeHuff(String fileName)
   {
      String huffFile = fileName.substring(0, fileName.length()-4) + ".huff";
           
      try
      {
         PrintStream out = new PrintStream(huffFile); //creating file to print to
         for(Character c: list) 
         {
            out.print(map2.get(c));//prints the huff value to file
         }
         out.close();
      }
      catch (Exception e) 
      {
        System.out.println (e.toString());
        System.out.println("File Not Found");
      }
   }


}