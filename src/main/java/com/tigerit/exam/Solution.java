package com.tigerit.exam;
import java.util.*;
import java.lang.*;

import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {

    public static ArrayList<Integer> createCopy (ArrayList<Integer> orginal) {
          ArrayList<Integer> copy = new ArrayList<Integer>();
          for (Integer s:orginal) {
            copy.add(s);
          }
          return copy;
        }
        
    @Override
    public void run() {
        Scanner sc= new Scanner(System.in);
        int tests,queries;
        tests=sc.nextInt();
        for(int cs=1;cs<=tests;cs++)
        {
            int nT=sc.nextInt();
            sc.nextLine(); //*******************//
            int column[]=new int[105];
            int row[]=new int[105];
            
            String colName[][]=new String[105][105];
            int values[][][]=new int[105][105][105];
            ArrayList<String> tables = new ArrayList<String>();
            HashMap<String, Integer> tableIndex = new HashMap<String, Integer>();
            
            HashMap<String, String> mapTable = new HashMap<String, String>();
            
            for(int i=0;i<nT;i++)
            {
                String name=sc.next();
                tables.add(name);
                
                tableIndex.put(name, i);
                
                column[i]=sc.nextInt();
                row[i]=sc.nextInt();
                sc.nextLine();
                
                for(int j=0;j<column[i];j++)
                {
                    colName[i][j]=sc.next();
                }
                
                for(int j=0;j<row[i];j++)
                {
                    for(int k=0;k<column[i];k++)
                    {
                        values[i][j][k]=sc.nextInt();
                    }
                }
            }
            
            queries=sc.nextInt();
            sc.nextLine();
            String SELECT,FROM,JOIN,ON;
            System.out.println("Test: "+cs);
            
            while(queries>0)
            {
                queries--;
                SELECT=sc.nextLine();
                FROM=sc.nextLine();
                JOIN=sc.nextLine();
                ON=sc.nextLine();
                
                //System.out.println(SELECT);
                
                String tbl1="", tbl2="", tmp="";
                for(int i=5;i<FROM.length();i++)
                {
                    if(FROM.charAt(i)==' ')
                    {
                        tbl1=tmp;
                        tmp="";
                    }
                    else
                        tmp+=FROM.charAt(i);
                }
                if(tbl1.length()==0)
                    tbl1=tmp;
                else if(tmp.length()!=0)
                    mapTable.put(tmp,tbl1);
                mapTable.put(tbl1, tbl1);
                
                tmp="";
                for(int i=5;i<JOIN.length();i++)
                {
                    if(JOIN.charAt(i)==' ')
                    {
                        tbl2=tmp;
                        tmp="";
                    }
                    else
                        tmp+=JOIN.charAt(i);
                }
                
                if(tbl2.length()==0)
                    tbl2=tmp;
                else if(tmp.length()!=0)
                    mapTable.put(tmp, tbl2);
                mapTable.put(tbl2, tbl2);
                
                String firstTable="", firstColumn="", secondTable="", secondColumn="";
                tmp="";
                int loc=3;
                for(loc=3;loc<ON.length();loc++)
                {
                    //System.out.println("char at "+loc+" is "+ON.charAt(loc));
                    if(ON.charAt(loc)=='.')
                    {
                        loc++;
                        break;
                    }
                    firstTable+=ON.charAt(loc);
                    //System.out.println(ON.charAt(loc));
                }
                //System.out.println(firstTable);
                //System.out.println("==="+firstTable);
                firstTable=mapTable.get(firstTable);
                
                for(; loc<ON.length();loc++)
                {
                    if(ON.charAt(loc)==' ')
                    {
                        loc+=3;
                        break;
                    }
                    firstColumn+=ON.charAt(loc);
                }
                
                for(; loc<ON.length();loc++)
                {
                    if(ON.charAt(loc)=='.')
                    {
                        loc++;
                        break;
                    }
                    secondTable+=ON.charAt(loc);
                }
                secondTable=mapTable.get(secondTable);
                
                for(; loc<ON.length();loc++)
                {
                    secondColumn+=ON.charAt(loc);
                }
                
                String tbl="",col="";
                //System.out.println("----"+firstTable);
                int fst=tableIndex.get(firstTable);
                int scd=tableIndex.get(secondTable);
                int fsidx=0,scidx=0;
                
                for(int i=0;i<column[fst];i++)
                {
                    if(colName[fst][i].equals(firstColumn))
                    {
                        fsidx=i;
                        break;
                    }
                }
                
                for(int i=0;i<column[scd];i++)
                {
                    if(colName[scd][i].equals(secondColumn))
                    {
                        scidx=i;
                        break;
                    }
                }
                
                ArrayList<Integer> checkerF = new ArrayList<Integer>();
                ArrayList<String> checkerS = new ArrayList<String>();
                
                for(int i=0;i<column[fst];i++)
                {
                    checkerF.add(fst);
                    checkerS.add(colName[fst][i]);
                }
                
                for(int i=0;i<column[scd];i++)
                {
                    checkerF.add(scd);
                    checkerS.add(colName[scd][i]);
                }
                ArrayList<ArrayList <Integer> > ans = new ArrayList<ArrayList <Integer> >();
                ArrayList <Integer> vec = new ArrayList<Integer>();
                
                for(int i=0;i<row[fst];i++)
                {
                    vec.clear();
                    for(int j=0;j<row[scd];j++)
                    {
                        if(values[fst][i][fsidx]==values[scd][j][scidx])
                        {
                            for(int k=0;k<column[fst];k++)
                            {
                                vec.add(values[fst][i][k]);
                            }
                            for(int k=0;k<column[scd];k++)
                            {
                                vec.add(values[scd][j][k]);
                            }
                        }
                    }
                    if(vec.size()!=0)
                    {
                        //System.out.println(vec.size());
                        //ans.add(vec);
                        ans.add(createCopy(vec));
                        //System.out.println(ans.get(0).size());
                    }
                }
                //System.out.println(ans.get(0));
                int mxR=-1,mxC=-1;
                ArrayList<String> showcol= new ArrayList<String>();
                
                int[][] arr=new int[105][105];
                int r=0,c=0;
                
                if(SELECT.length()==8 && SELECT.charAt(7)=='*')
                {
                    for(int i=0;i<checkerF.size();i++)
                    {
                        showcol.add(checkerS.get(i));
                    }
                    mxR=ans.size();
                    //System.out.println(ans.get(0));
                    mxC=ans.get(0).size();
                    //System.out.println(",,,,,,"+mxC);
                    for(int i=0;i<mxR;i++)
                    {
                        for(int j=0;j<ans.get(i).size();j++)
                        {
                            //System.out.println(arr[i][j]);
                            arr[i][j]=ans.get(i).get(j);
                        }
                    }
                }
                else
                {
                    for(int i=7;i<SELECT.length();i++)
                    {
                        tbl="";
                        col="";
                        while(SELECT.charAt(i)!='.')
                        {
                            tbl+=SELECT.charAt(i);
                            i++;
                        }
                        i++;
                        while(SELECT.charAt(i)!=',' && i<SELECT.length())
                        {
                            col+=SELECT.charAt(i);
                            i++;
                            if(i>=SELECT.length())
                                break;
                        }
                        
                        tbl=mapTable.get(tbl);
                        int id=tableIndex.get(tbl);
                        
                        //System.out.println(tbl+"---"+col+" "+id);
                        
                        for(int j=0;j<checkerF.size();j++)
                        {
                            if(checkerF.get(j)==id && checkerS.get(j).equals(col))
                            {
                                //System.out.println(id+" "+col);
                                showcol.add(checkerS.get(j));
                                loc=j;
                                
                                for(int p=0;p<ans.size();p++)
                                {
                                    arr[r++][c]=ans.get(p).get(j);
                                    //System.out.println(ans.get(p).get(j));
                                    
                                }
                                mxR=Math.max(r, mxR);
                                r=0;
                                c++;
                                mxC=Math.max(c, mxC);
                                
                            }
                        }
                        i++;
                    }
                }
                for(int i=0;i<showcol.size();i++)
                {
                    if(i==showcol.size()-1)
                        System.out.println(showcol.get(i));
                    else
                        System.out.print(showcol.get(i)+" ");
                }
                int [][] brr=new int[mxR][mxC];
				for(int i=0;i<mxR;i++)
				{
					for(int j=0;j<mxC;j++)
					{
						//System.out.println(arr[i][j]);
						brr[i][j]=arr[i][j];
					}
				}
				Arrays.sort(brr, Arrays::compare);
				for(int i=0;i<mxR;i++)
				{
					for(int j=0;j<mxC;j++)
					{
						if(j==mxC-1)
							System.out.println(brr[i][j]);
						else
							System.out.print(brr[i][j]+" ");
					}
				}
				System.out.println();
            }
            
        }
    }
}
