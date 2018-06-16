/**
 * Created by Yifan on 2018/3/22.
 * Each train contains two parameters, which are departure who send the group to the destination who receive the group.
 * The CableCar extends Village, the sendGroup() and receiveGroup() methods in CableCar is override.
 */
public class Train extends Thread{
    private Village departure = null;
    private Village destination = null;

    public Train(Village departure, Village destination){
        this.departure = departure;
        this.destination = destination;
    }

    public void run(){
        while (!interrupted()){
            try{
                departure.sentGroup();
                sleep(Params.JOURNEY_TIME);
                destination.receiveGroup(departure.getStoredVisitor());
            }catch (Exception e){}
        }
    }

}
