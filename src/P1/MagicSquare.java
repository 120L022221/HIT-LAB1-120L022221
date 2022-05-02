package P1;
import java.io.*;
import java.util.*;
public class MagicSquare {
    public static boolean isLegalMagicSquare(String fileName) {
        //打开文件，无文件则返回“FileNotFound”
        FileReader reader;
        try{  reader=new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound");
            e.printStackTrace();
            return false;
        }
        BufferedReader br = new BufferedReader(reader);
        String myLine;//每次读的一行
        String[] Line;//分隔符处理后的
        int[] IntLine;//转为整形的
        int[][] Square;
        int col_num=0;//幻方大小
        //用来判断是否包含两个相同元素的判定数组
        int[] ifSame=new int[30000];
        for(int i=0;i<30000;i++) {
            ifSame[i] = 0;
        }
        try {
            //按行读文件
            myLine=br.readLine();
            //分隔符的处理
            try{Line=myLine.split("\t");
            }catch (Exception e)
            {
                System.out.println("输入的幻方边长n为0");
                return false;
            }
            //幻方大小
            col_num= Line.length;
            IntLine=new int[col_num];
            Square=new int[col_num][col_num];
            int col=0,row=0,tem=0;
            //把元素存到Square数组中
            for (String s : Line) {
                //判断是否为正整数
                if((tem=Integer.parseInt(s))<=0)
                {
                    System.out.println("矩阵中的某些数字输入不合法，请输入正整数");
                    return false;
                }
                Square[row][col++] = Integer.parseInt(s);
            }
            row++;
            //按行读文件
            while ((myLine= br.readLine())!=null) {
                col=0;
                //判断行列数是否相等
                if(row>=col_num)
                {
                    System.out.println("行列数不相等");
                    return false;
                }
                //分隔符处理
                Line=myLine.split("\t");
                //判断元素是不是正整数
                for (String s : Line) {
                    if(Integer.parseInt(s)<=0)
                    {
                        System.out.println("矩阵中的某些数字输入不合法，请输入正整数");
                        return false;
                    }
                    Square[row][col++] = Integer.parseInt(s);
                }
                row++;
                //判断是不是矩阵
                if(col_num!=col){
                    System.out.println("并非矩阵");
                    return false;
                }
            }
            //判断行列数
            if(row<col_num)
            {
                System.out.println("行列数不相等");
                return false;
            }
        } catch (Exception e) {
            System.out.println("非法的输入格式");
            e.printStackTrace();
            return false;
        }
        //格式正常，判断是否有重复数字
        int temp=0;
        for(int i=0;i<col_num;i++)
        {
            for(int j=0;j<col_num;j++)
            {
                temp=Square[i][j];
                if(ifSame[temp]==1)
                {
                    System.out.println("有重复数字，不符合幻方定义");
                    return false;
                }
                ifSame[temp]=1;
            }
        }
        //判断行、列、对角线的和
        int row_sum=0;
        int col_sum=0;
        int tem=0;
        int diag_sum1=0,diag_sum2=0;
        int count_j=0;
        int count_i=0;
        //第一行的和
        for(count_j=0;count_j<col_num;count_j++)
        {
            row_sum=row_sum+Square[0][count_j];
        }
        //后续行的和
        for(count_i=1;count_i<col_num;count_i++)
        {
            for(count_j=0;count_j<col_num;count_j++)
            {
                tem=tem+Square[count_i][count_j];
            }
            if(tem!=row_sum)
            {
                System.out.println("行不等");
                return false;
            }
            tem=0;
        }
        //判断列的和
        for(count_j=0;count_j<col_num;count_j++)
        {
            for(count_i=0;count_i<col_num;count_i++)
            {
                tem=tem+Square[count_i][count_j];
            }
            if(tem!=row_sum)
            {
                System.out.println("列不等");
                return false;
            }
            tem=0;
        }
        //判断两个对角线的和
        for(int s=0;s<col_num;s++)
        {
            diag_sum1=diag_sum1+Square[s][s];
            diag_sum2=diag_sum2+Square[s][col_num-1-s];
        }
        if(diag_sum1!=row_sum||diag_sum2!=row_sum)
        {
            System.out.println("对角线不等");
            return false;
        }
        return true;
    }
    public static boolean generateMagicSquare(int n)  {
        int[][] magic;
        try{ magic= new int[n][n];
        }catch (NegativeArraySizeException e){
            //负数异常
            System.out.println("输入的是负数");
            e.printStackTrace();
            return false;
        }
        int row = 0, col = n / 2, i, j, square = n * n;
        //n*n次循环
        for (i = 1; i <= square; i++) {
            try {
                magic[row][col] = i; // 赋值
            } catch (ArrayIndexOutOfBoundsException e) {
                // 偶数异常
                System.out.println("输入的是偶数");
                e.printStackTrace();
                return false;
            }
            //往右上移动
            if (i % n == 0)
                row++;//完成一次斜线的操作
            else {
                if (row == 0)
                    row = n - 1;//超出边界，从第一行跳转至第n行
                else
                    row--;//从下往上

                if (col == (n - 1))
                    col = 0;//超出边界，返回第一列
                else
                    col++;//从左往右
            }
        }
        //输出幻方内的元素
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        //写文件得到6.txt
        File file6 =new File("src/P1/txt/6.txt");

        try {
            if(!file6.createNewFile())
            {
                System.out.println("已存在6.txt文件，新建文件失败");
                return false;
            }

            //写文件
            FileWriter fw;
            fw=new FileWriter(file6);
            BufferedWriter bw=new BufferedWriter(fw);
            for (int write_row = 0; write_row < n; write_row++) {
                for (int write_col = 0; write_col < n; write_col++) {
                    bw.write(magic[write_row][write_col] + "\t");
                }
                bw.write("\n");
            }
            bw.flush();//刷新缓冲流，把数据写入到目标文件里
            bw.close();//关闭输入流之前刷新一下该缓冲流
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static void main(String[] args){
        boolean T1,T2,T3,T4,T5,T6;
        int n;
        Scanner input = new Scanner(System.in);
        System.out.println("输入参数n");
        n = input.nextInt();
        input.close();

        System.out.println("1.txt"+":");T1=isLegalMagicSquare("src/P1/txt/1.txt");System.out.println(T1);
        System.out.println("2.txt"+":");T2=isLegalMagicSquare("src/P1/txt/2.txt");System.out.println(T2);
        System.out.println("3.txt"+":");T3=isLegalMagicSquare("src/P1/txt/3.txt");System.out.println(T3);
        System.out.println("4.txt"+":");T4=isLegalMagicSquare("src/P1/txt/4.txt");System.out.println(T4);
        System.out.println("5.txt"+":");T5=isLegalMagicSquare("src/P1/txt/5.txt");System.out.println(T5);
        if(generateMagicSquare(n))
        {
            System.out.println("6.txt"+":");T6=isLegalMagicSquare("src/P1/txt/6.txt");System.out.println(T6);
        }
    }

}

