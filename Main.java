import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Main {

    static String fileoutput = "src\\output\\e_many_teams.txt";
    static String fileinput = "src\\e_many_teams.in";

    public static void main(String[] args) throws Exception{
     Pizzaclass pizzaclass =  ReadFileData(fileinput);
        Outputclass outputclass = PizzaDelivery(pizzaclass);
        WriteToFile(outputclass);
    }

    private static Outputclass PizzaDelivery(Pizzaclass pizzaclass) {
        int totalpeople;
        ArrayList<deliveredpizzas> deliveredpizzasArrayList = new ArrayList<>();
        Outputclass outputclass = new Outputclass();
        outputclass.numOfDeliveredPizzas=0;
        for (int i=0;i<pizzaclass.availablePizza;){
            // these three lines
             totalpeople = (pizzaclass.team_of_2*2)+(pizzaclass.team_of_3*3)+(pizzaclass.team_of_4*4);
                if (totalpeople<deliveredpizzasArrayList.size())
                    break;

            if (pizzaclass.team_of_2!=0){
                if (i+1 >= pizzaclass.availablePizza)
                    break;
                ArrayList<Integer> pizzaIdx = new ArrayList<>();
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i).id);
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i+1).id);
                deliveredpizzasArrayList.add(new deliveredpizzas(2,pizzaIdx));

                pizzaclass.team_of_2--;
                i+=2;
                outputclass.deliveredpizzas = deliveredpizzasArrayList;
            }else if (pizzaclass.team_of_3!=0){
                if (i+2 >= pizzaclass.availablePizza)
                    break;
                ArrayList<Integer> pizzaIdx = new ArrayList<>();
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i).id);
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i+1).id);
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i+2).id);
                deliveredpizzasArrayList.add(new deliveredpizzas(3,pizzaIdx));

                pizzaclass.team_of_3--;
                i+=3;
                outputclass.deliveredpizzas = deliveredpizzasArrayList;
            }else if (pizzaclass.team_of_4!=0){
                if (i+3 >= pizzaclass.availablePizza)
                    break;
                ArrayList<Integer> pizzaIdx = new ArrayList<>();
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i).id);
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i+1).id);
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i+2).id);
                pizzaIdx.add(pizzaclass.pizzaIngredients.get(i+3).id);
                deliveredpizzasArrayList.add(new deliveredpizzas(4,pizzaIdx));

                pizzaclass.team_of_4--;
                i+=4;
                outputclass.deliveredpizzas = deliveredpizzasArrayList;
            }
            outputclass.numOfDeliveredPizzas = deliveredpizzasArrayList.size();
        }
        return outputclass;
    }

    private static void WriteToFile(Outputclass outputclass) throws Exception{
        FileOutputStream outputStream = new FileOutputStream(fileoutput);
        String outputtext = outputclass.numOfDeliveredPizzas + "\n";
        for (deliveredpizzas pizza: outputclass.deliveredpizzas){
            StringBuilder builder = new StringBuilder("");
            for(int num:pizza.pizzaIndex){
                builder.append(num+"").append(" ");
            }
            outputtext+= pizza.teamtype + " " + builder + "\n";
        }
        outputStream.write(outputtext.getBytes(StandardCharsets.UTF_8));
    }

    private static Pizzaclass ReadFileData(String s) throws Exception {
        File file = new File(s);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        ArrayList<String> strings = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            strings.add(st);
        }

        ArrayList<pizzaIngredients> pizzaIngredients = new ArrayList<>();

        int aPizzas=0,T2=0,T3=0,T4=0;
        String temp = strings.get(0);
            String[] numberString = temp.split(" ");
            int[] numbers = new int[temp.length()];
            for (int j=0;j<4;j++){
                numbers[j] = Integer.parseInt(numberString[j]);
            }
            aPizzas = numbers[0];
            T2 = numbers[1];
            T3 = numbers[2];
            T4 = numbers[3];
        for(int i=1;i<strings.size();i++){
            String[] temporary = strings.get(i).split(" ");
            int num = Integer.parseInt(temporary[0]);
            ArrayList<String> pizzas = new ArrayList<>();

            for(int j=1;j<temporary.length;j++){
                pizzas.add(temporary[j]);
            }
            pizzaIngredients.add(new pizzaIngredients(num,pizzas,i-1));
        }

        Pizzaclass pizzaclass_obj = new Pizzaclass(aPizzas,T2,T3,T4,pizzaIngredients);
        return pizzaclass_obj;
    }
}

class Pizzaclass{
    public int availablePizza;
    public int team_of_2;
    public int team_of_3;
    public int team_of_4;
    public ArrayList<pizzaIngredients> pizzaIngredients;

    public Pizzaclass(int availablePizza, int team_of_2, int team_of_3, int team_of_4, ArrayList<pizzaIngredients> pizzaIngredients) {
        this.availablePizza = availablePizza;
        this.team_of_2 = team_of_2;
        this.team_of_3 = team_of_3;
        this.team_of_4 = team_of_4;
        this.pizzaIngredients = pizzaIngredients;
    }

    @Override
    public String toString() {
        return "Pizzaclass{" +
                "availablePizza=" + availablePizza +
                ", team_of_2=" + team_of_2 +
                ", team_of_3=" + team_of_3 +
                ", team_of_4=" + team_of_4 +
                ", pizzaIngredients=" + pizzaIngredients +
                '}';
    }
}

class pizzaIngredients{
    public int number_of_ingredients;
    public ArrayList<String> ingredients;
    public int id;

    public pizzaIngredients(int number_of_ingredients, ArrayList<String> ingredients,int id) {
        this.number_of_ingredients = number_of_ingredients;
        this.ingredients = ingredients;
        this.id=id;
    }

    @Override
    public String toString() {
        return "pizzaIngredients{" +
                "number_of_ingredients=" + number_of_ingredients +
                ", ingredients=" + ingredients +
                '}';
    }
}

class Outputclass{
    public int numOfDeliveredPizzas;
    public ArrayList<deliveredpizzas> deliveredpizzas=new ArrayList<>();

}

class deliveredpizzas{
    public int teamtype;
    public ArrayList<Integer> pizzaIndex;

    public deliveredpizzas(int teamtype, ArrayList<Integer> pizzaIndex) {
        this.teamtype = teamtype;
        this.pizzaIndex = pizzaIndex;
    }
}