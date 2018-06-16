/**
 * Created by Yifan on 2018/3/22.
 * Operate the cable car when they are not in use, change its location.
 */
public class Operator extends Thread {

    private CableCar cableCar;

    public Operator(CableCar cableCar) {
        this.cableCar = cableCar;
    }

    public void run(){
        while (!interrupted()){
            try{
                cableCar.descend();
                sleep(Params.OPERATE_TIME);
                cableCar.ascend();

                sleep(Params.operateLapse());
            }catch (InterruptedException e){
                this.interrupt();
            }
        }

    }
}
