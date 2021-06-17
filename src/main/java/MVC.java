public class MVC {
    public class Model {
        private String name;
        private int prize;

        public Model(String name, int prize) {
            this.name = name;
            this.prize = prize;
        }

        public String getName() {
            return name;
        }

        public int getPrize() {
            return prize;
        }
    }

    public class View {
        public void displayItem(String name, int prize) {
            System.out.println(name + " " + prize);
        }
    }

    public class Controller {
        private Model model;
        private View view;

        public Controller(Model model, View view) {
            this.model = model;
            this.view = view;
        }
        private void printItem() {
            view.displayItem(model.getName(), model.getPrize());
        }
    }
}
