package sample;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private SimpleStringProperty order;
    private SimpleStringProperty step;

    public Person(String order,String step){
        this.order=new SimpleStringProperty(order);
        this.step=new SimpleStringProperty(step);
    }
    public void setOrder(String order){
        this.order.set(order);
    }
    public void setStep(String step){
        this.step.set(step);
    }
    public String getOrder(){
        return order.get();
    }
    public String getStep(){
        return step.get();
    }

}
