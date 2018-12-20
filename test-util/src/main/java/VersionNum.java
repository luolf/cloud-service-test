public class VersionNum {
    public static void main(String[] args)  {


        String[] s=new String[]{"1.1.2","1.10.2","0.0.2","1.0.1","1.0.0.1"};
        for(String tmp:s){
            System.out.println(numString(tmp)+":"+tmp);
        }
    }
    public static String  numString(String s){
        String[] tmps=s.split("\\.");

        StringBuilder rst= new StringBuilder();
                for(String tmp:tmps){
                    rst.append(100+Integer.parseInt(tmp)).append(".");
                }
                return rst.toString();

    }
}
