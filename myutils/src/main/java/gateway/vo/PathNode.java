package gateway.vo;

import java.util.ArrayList;
import java.util.List;

public class PathNode {
    String name;
    List<PathNode> subNodes;
    String Value;

    public PathNode() {
        subNodes =new ArrayList<>();
    }

    public PathNode(String name) {
        if(!name.startsWith("/")){
            this.name ="/"+ name;
        }else {
            this.name = name;
        }
        subNodes =new ArrayList<>();
    }

    public PathNode(String name, String value) {
        if(!name.startsWith("/")){
            this.name ="/"+ name;
        }else {
            this.name = name;
        }
        Value = value;
        subNodes =new ArrayList<>();
    }

    public List<PathNode> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(List<PathNode> subNodes) {
        this.subNodes = subNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
