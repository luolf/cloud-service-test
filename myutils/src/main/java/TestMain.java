import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luolifeng
 * Date: 2018-12-18
 * Time: 15:57
 */
public class TestMain {
    class A{
        String test1;

        public String getTest1() {
            return test1;
        }

        public void setTest1(String test1) {
            this.test1 = test1;
        }
    }
    class B extends  A{
        String test;

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }
    public static void  main(String[] args){
        TestMain.B b=new TestMain().new B();
        b.setTest("i come from b");
        b.setTest1("i come from a");
        TestMain.A a=b;
        System.out.println(a);
        System.out.println(b);
        String prex="cc";
        String sNUll=a.getTest1();
        String sempty="";
        String v1="1.0.0";
        String v2="1.2.1";

        sNUll=prex+sNUll;
        sempty=prex+sempty;
        v1=prex+v1;
        v2=prex+v2;
        System.out.println(sempty);
        System.out.println(sNUll);
        System.out.println(v1.compareTo(v2));
        System.out.println(v1.compareTo(sempty));
        System.out.println(v1.compareTo(sNUll));
        System.out.println(v1.compareTo(v2));
        System.out.println(sempty.compareTo(sNUll));
    }
}
