import java.util.*;
import java.io.*;
/**
 * This class contains the methods to read a .huff and .code file and decipher their data
 *
 * @author Ashleen Sandhu
 * @version 06/09/22
 */
public class CodeToText
{
   /** 
   *This is the main driver function called by the o.s.
   *@param args Program variables from the o.s.
   */
   public static void main(String[] args)
   {
      //asking for user input
      Scanner s = new Scanner(System.in);
      System.out.println("please enter a text file name: ");
      String fileName = s.nextLine();
      
      File[] files = new File[2];
      getFiles(files, fileName);
      File huffFile = files[0];
      File codeFile = files[1];
      
      Map<String, Character> stringMap = new HashMap<String, Character>();
      readCode(stringMap, codeFile);
      readHuff(stringMap, huffFile);
   }
     /** This method creates the file.
   *
   * @param files An array to add the file objects tp.
   * @param fileName The string the user entered in the main. 
   */
    public static void getFiles(File[] files, String fileName)
    {
      //getting file from user
      while(!fileName.endsWith(".code") && !fileName.endsWith(".huff"))    
      {
         Scanner s = new Scanner(System.in);
         System.out.println("Please re-enter file name");
         fileName = s.nextLine();
      }
      
      File huffFile = null;
      File codeFile = null;
     //checking file to see if it exists and then making it into a .huff and .code
      try
      {
         if(fileName.endsWith(".huff"))
         {            
            huffFile = new File(fileName);
            codeFile = new File(fileName.substring(0, fileName.length()-4) + "code");
         } 
         else if( fileName.endsWith(".code"))
         {            
            codeFile = new File(fileName);
            huffFile = new File(fileName.substring(0, fileName.length()-4) + "huff");
         }
         else
         {
            huffFile = new File(fileName + ".huff");
            codeFile = new File(fileName + ".code");
         }     
      }
      
      catch(Exception e)
      {
         System.out.println(e.toString());
         System.out.println("FIle " + fileName + " Not found");
      }
      
      files[0]  = huffFile;
      files[1] = codeFile;      
   }
     /** This method reads a .code file and adds the char and huff values to a map.
   *
   * @param stringMap An empty map to add the char and the huff values from the file.
   * @param codeFile The file that contains the int representation of the char and the huff value.
   */
   public static void readCode(Map<String, Character> stringMap, File codeFile)
   {
      try
      {
         Scanner file = new Scanner(codeFile);
         while(file.hasNextLine())
         {
            Character c = (char) file.nextInt();
            file.nextLine();
            String s = file.nextLine();
            stringMap.put(s,c);
         }
      }
      
      catch(Exception e)
      {
         System.out.println(e.toString());
         System.out.println("File Not found");
      }
   }
     /** This method read a .huff file and prints out the chars that the letters in the string represent.
   *
   * @param stringMap A map of char and the huff value that corresponds to it.
   * @param huffFile The file that contains the huff string .
   */
   public static void readHuff(Map<String, Character> stringMap, File huffFile)
   {
      try
      {
         Scanner file = new Scanner(huffFile);
         String data = file.nextLine();
         String s = "";
         
         for(int i=0; i<data.length(); i++)
         {
            s += data.charAt(i);
            
            if(stringMap.containsKey(s))
            {
               System.out.print(stringMap.get(s));
               s="";
            }
         }
         
      }
      catch(Exception e)
      {
         System.out.println(e.toString());
         System.out.println("File Not found");
      }
   }

}