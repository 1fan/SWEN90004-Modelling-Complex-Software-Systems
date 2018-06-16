import java.util.Random;

/**
 * Created by Yifan on 2018/3/22.
 * Village is a monitor as well. It defines functions of send a group and receive a group.
 * It is the father class of the class CableCar.
 */
public class Village {
    protected Group group = null;
    protected Integer villageID = null;
    protected Village nextV = null;
    protected Group StoredVisitor = null;

    public Group getStoredVisitor(){
        return StoredVisitor;
    }

    public Village(int i) {
        this.villageID = i;
        this.group = null;
    }

    public Village() {

    }

    public void setNextV(Village v){
        nextV = v;
    }

    /*The group is sent when the village is not empty, and the next village is empty. Otherwise wait.
     After sending the group, clear the group information in this village and notify all.*/
    public synchronized void sentGroup(){
        while (group==null && nextV.group!=null){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        System.out.println("["+group.getId()+"] leaves village "+villageID);
        StoredVisitor = this.group;
        this.group = null;
        notifyAll();
    }

    /*The group is received when the village is empty. Otherwise wait.
    After receiving the group,set the group information in this village and notify all.*/
    public synchronized void receiveGroup(Group group){
        if(group == null){
            System.out.println("NULL");
        }
        while (this.group!=null){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        this.group = group;
        System.out.println("["+group.getId()+"] enters village"+ villageID);
        notifyAll();
    }

    public Group getGroup(){
        return group;
    }
}
