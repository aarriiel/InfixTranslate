package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    @FXML
    private TextField prefix;
    @FXML
    private TextField infix;
    @FXML
    private TextField postfix;
    @FXML
    private TextField prefixValue;
    @FXML
    private TextField postfixValue;
    @FXML
    private Button translate;
    @FXML
    private Button clear;
    @FXML
    private TableColumn prefixViewOrder;
    @FXML
    private TableColumn postfixViewOrder;
    @FXML
    private TableColumn prefixValueViewOrder;
    @FXML
    private TableColumn postfixValueViewOrder;
    @FXML
    private TableColumn postfixValueViewStep;
    @FXML
    private TableColumn prefixViewStep;
    @FXML
    private TableColumn postfixViewStep;
    @FXML
    private TableColumn prefixValueViewStep;
    @FXML
    private TableView postfixValueView;
    @FXML
    private TableView prefixValueView;
    @FXML
    private TableView postfixView;
    @FXML
    private TableView prefixView;
    private Stack stack;
    private StackArray stackArray;
    String output="";
    public void initialize(){
        prefix.setDisable(true);
        postfix.setDisable(true);
        prefixValue.setDisable(true);
        postfixValue.setDisable(true);
        prefixViewOrder.setCellValueFactory(new PropertyValueFactory<Person,String>("order"));
        prefixViewStep.setCellValueFactory(new PropertyValueFactory<Person,String>("step"));
        postfixViewOrder.setCellValueFactory(new PropertyValueFactory<Person,String>("order"));
        postfixViewStep.setCellValueFactory(new PropertyValueFactory<Person,String>("step"));
        prefixValueViewOrder.setCellValueFactory(new PropertyValueFactory<Person,String>("order"));
        prefixValueViewStep.setCellValueFactory(new PropertyValueFactory<Person,String>("step"));
        postfixValueViewOrder.setCellValueFactory(new PropertyValueFactory<Person,String>("order"));
        postfixValueViewStep.setCellValueFactory(new PropertyValueFactory<Person,String>("step"));
        translate.setOnAction(e -> control(1));
        clear.setOnAction(e -> control(2));
    }
    public void control(int i){
        switch (i){
            case 1:
                String input=infix.getText();
                stack=new Stack(input.length());
                for(int a=0;a<input.length();a++){//postfix
                    char part=input.charAt(a);
                    char tra;
                    switch (part){
                        case '+':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) >= precedence(part))
                                output=output+stack.pop();
                            stack.push(part);
                            break;
                        case '-':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) >= precedence(part))
                                output=output+stack.pop();
                            stack.push(part);
                            break;
                        case '*':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) >= precedence(part))
                                output=output+stack.pop();
                            stack.push(part);
                            break;
                        case '/':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) >= precedence(part))
                                output=output+stack.pop();
                            stack.push(part);
                            break;
                        case '(':
                            stack.push(part);
                            break;
                        case ')':
                            while ((tra = stack.pop()) != '(') {
                                output += tra;
                            }
                            break;
                        default:
                            output=output+part;
                            break;
                    }
                    Person person = new Person(String.valueOf(part),output);
                    postfixView.getItems().add(person);
                }
                while (!stack.isEmpty()) {
                    output = output + stack.pop();
                }
                Person person = new Person("fianl",output);
                postfixView.getItems().add(person);
                postfix.setText(output);
                output="";
                for (int a=input.length()-1;a>=0;a--){//prefix
                    char part=input.charAt(a);
                    char tra;
                    switch (part){
                        case '+':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) > precedence(part))
                                output=stack.pop()+output;
                            stack.push(part);
                            break;
                        case '-':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) > precedence(part))
                                output=stack.pop()+output;
                            stack.push(part);
                            break;
                        case '*':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) > precedence(part))
                                output=stack.pop()+output;
                            stack.push(part);
                            break;
                        case '/':
                            while(!stack.isEmpty()&& part != '(' && precedence(stack.peek()) > precedence(part))
                                output=stack.pop()+output;
                            stack.push(part);
                            break;
                        case ')':
                            stack.push(part);
                            break;
                        case '(':
                            while ((tra = stack.pop()) != ')') {
                                output = tra+output;
                            }
                            break;
                        default:
                            output=part+output;
                            break;
                    }
                    Person person1 = new Person(String.valueOf(part),output);
                    prefixView.getItems().add(person1);
                }
                while (!stack.isEmpty()) {
                    output = stack.pop()+output;
                }
                Person person1 = new Person("final",output);
                prefixView.getItems().add(person1);
                prefix.setText(output);
                output="";
                input=prefix.getText();
                stackArray=new StackArray();
                for(int a=input.length()-1;a>=0;a--){
                    char part=input.charAt(a);
                    if(part=='+'||part=='-'||part=='*'||part=='/'){
                        int x=stackArray.Pop();
                        int y=stackArray.Pop();
                        stackArray.Push(stackArray.TwoResult(part,x,y));
                        output=String.valueOf(x)+part+String.valueOf(y);
                        Person person2 = new Person(String.valueOf(part),output);
                        prefixValueView.getItems().add(person2);
                    }
                    else{
                        int num = part- '0';
                        stackArray.Push(num);
                        output=String.valueOf(part);
                        Person person2 = new Person(String.valueOf(part),output);
                        prefixValueView.getItems().add(person2);
                    }
                }
                int fin=stackArray.Pop();
                output=String.valueOf(fin);
                Person person2 = new Person("final",output);
                prefixValueView.getItems().add(person2);
                prefixValue.setText(String.valueOf(fin));
                output="";
                input=postfix.getText();
                for(int a=0;a<input.length();a++){
                    char part=input.charAt(a);
                    if(part=='+'||part=='-'||part=='*'||part=='/'){
                        int x=stackArray.Pop();
                        int y=stackArray.Pop();
                        stackArray.Push(stackArray.TwoResult1(part,x,y));
                        output=String.valueOf(y)+part+String.valueOf(x);
                        Person person3 = new Person(String.valueOf(part),output);
                        postfixValueView.getItems().add(person3);
                    }
                    else{
                        int num = part- '0';
                        stackArray.Push(num);
                        output=String.valueOf(part);
                        Person person3 = new Person(String.valueOf(part),output);
                        postfixValueView.getItems().add(person3);
                    }
                }
                fin=stackArray.Pop();
                output=String.valueOf(fin);
                Person person3 = new Person("final",output);
                postfixValueView.getItems().add(person3);
                postfixValue.setText(String.valueOf(fin));
                output="";
                break;
            case 2:
                infix.setText("");
                postfix.setText("");
                prefix.setText("");
                postfixValue.setText("");
                prefixValue.setText("");
                prefixView.getItems().clear();
                postfixView.getItems().clear();
                prefixValueView.getItems().clear();
                postfixValueView.getItems().clear();
                break;
        }

    }
    private static int precedence(char i) {

        if (i == '(' || i == ')') return 1;
        else if (i == '-' || i == '+') return 2;
        else if (i == '*' || i == '/') return 3;
        else return 0;
    }
    class StackArray
    {
        int MaxSize = 20;
        int[] AStack = new int[MaxSize];		// 宣告堆疊陣列
        int Top = -1;					// 堆疊頂端
        public void Push(int Value)
        {
                Top++;
                AStack[Top] = Value;
        }
        public int Pop() {
            int Temp;
            Temp = AStack[Top];
            Top--;
            return Temp;
        }
        public int TwoResult(int operator,int operand2,int operand1)
        {
            switch (operator)
            {
                case 43:
                    return (operand2 + operand1);
                case 45:
                    return (operand2 - operand1);
                case 42:
                    return (operand2 * operand1);
                case 47:
                    return (operand2 / operand1);
            }
            return 0;
        }
        public int TwoResult1(int operator,int operand1,int operand2)
        {
            switch (operator)
            {
                case 43:
                    return (operand2 + operand1);
                case 45:
                    return (operand2 - operand1);
                case 42:
                    return (operand2 * operand1);
                case 47:
                    return (operand2 / operand1);
            }
            return 0;
        }
    }
    class Stack {
        private int maxSize;
        private char[] stackArrayy;
        private int top;

        public Stack(int max) {
            maxSize = max;
            stackArrayy = new char[maxSize];
            top = -1;
        }
        public void push(char j) {
            stackArrayy[++top] = j;
        }
        public char pop() {
            return stackArrayy[top--];
        }
        public char peek() {
            return stackArrayy[top];
        }
        public boolean isEmpty() {
            return (top == -1);
        }
    }
}