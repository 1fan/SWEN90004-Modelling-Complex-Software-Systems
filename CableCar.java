import javax.sound.midi.Soundbank;

/**
 * Created by Yifan on 2018/3/22.
 * The CableCar class is a monitor who provides synchronized functions to be called by queue processes.
 */
public class CableCar extends Village{

    protected boolean location = false; /*false means at the vally, true means at the terminus.*/

    public CableCar(int i) {
        super(i);
    }

    public CableCar() {
        this.group = null;
        this.location = false;
    }

    /*Only when the cable car is occupied, and the location of the cable car is at valley, and the group has finished
    the trip, could the group departs, otherwise this process has to wait.And notify all*/
    public synchronized void depart() {
        while (this.group==null || this.location || ((this.group!=null) && (this.group.getFinishTrip()==false))){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        System.out.println("["+this.group.getId()+"] departs");
        this.group = null;
        this.location = false;
        notifyAll();
    }

    /*Only when the cable car is empty, and the cable car is located at the valley, and the village0 is not been
    occupied could a newly group arrive. If the village0 has been occupied the group is not allowed to arrive otherwise
    these groups would jam at the terminus.*/
    public synchronized void arrive(Group group) {
        while (this.group != null || this.location || nextV.getGroup()!=null){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        System.out.println("["+group.getId()+"] enters cable car to go up");
        this.group = group;
        this.location = true;
        notifyAll();
    }

  /*If the cable car is empty and is located at the valley,
  Operator calls this function to change the location to terminus.*/
    public synchronized void ascend(){
        while (this.group!=null || this.location){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        System.out.println("cable car ascends");
        this.location = true;
        this.group = null;
        notifyAll();
    }

  /*If the cable car is empty and is located at the terminus,
  Operator calls this function to change the location to valley.*/
    public synchronized void descend(){
        while (this.group!=null || !this.location){
            try{
                wait();
            }catch (InterruptedException e){}
        }
        System.out.println("cable car descends");
        this.location = false;
        this.group = null;
        notifyAll();
    }

    /*Only when the cable car is been occupied, and the village0 is not been occupied, and the cable car is located
     at the terminus, and the group has finished the trip, could the cable car send the group from the terminus
     to village 0, otherwise wait. After sending, set clear the group info, set the location at the terminus.*/
    public synchronized void sentGroup(){
        while (this.group==null || nextV.getGroup()!=null || !this.location || this.group.getFinishTrip()){
            try {
                wait();
            }catch (InterruptedException e){}
        }
        this.StoredVisitor = this.group;
        this.group = null;
        this.location = true;
        System.out.println("["+this.StoredVisitor.getId()+"] leaves the cable car");
        notifyAll();
    }

    /*Only when the cable car is not occupied and the cable car is at the terminus could the cable car receive a group,
    otherwise wait. After receive the group, set the group information in this class, set the location of the cable car
    at the valley, and mark the group has finished the trip.*/
    public synchronized void receiveGroup(Group group){
        while (this.group != null || !this.location){
            try {
                wait();
            }catch (InterruptedException e){}
        }
        this.group = group;
        this.group.setFinishTrip(true);
        System.out.println("["+group.getId()+"] enters cable car to go down");
        this.location = false;
        notifyAll();
    }
}
