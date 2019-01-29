public class VersionNum {
    public static void main(String[] args)  {


        String[] s=new String[]{"1.1.2","1.10.2","0.0.2","1.0.1","1.0.0.1"};
        for(String tmp:s){
            System.out.println(numString(tmp)+":"+tmp);
        }

        long x = 10;
        int n = 3;
        System.out.println(pow(x,n));
    }
    public static String  numString(String s){
        String[] tmps=s.split("\\.");

        StringBuilder rst= new StringBuilder();
                for(String tmp:tmps){
                    rst.append(100+Integer.parseInt(tmp)).append(".");
                }
                return rst.toString();

    }
    public static long pow(long x,int n){
        if( n == 0 ){
            return 1;
        }
        if( n == 1 ){
            return x;
        }
        //偶数   相当于每次将x^n变为x^(n/2) * x^(n/2)
        if( n % 2 == 0 ){
            return pow(x * x, n / 2);
        }else {//奇数   相当于每次将x^n变为x^((n-1)/2) * x^((n-1)/2) * x
            //可以写成pow(x*x,n/2)   原因：(n-1)/2和n/2在Java中结果相同
            //可以写成pow(x,n-1)*2   原因：通过提出一个x，使n变为奇数
            return pow(x * x, (n-1) / 2) * x;
        }
    }

}
