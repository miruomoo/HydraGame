package se2203b.hydragame;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class HydraGameController implements Initializable {

    //declares fxml variables
    @FXML
    private GridPane grid;
    @FXML
    private Slider slider;
    @FXML
    private Button restart;
    @FXML
    private Button play;
    @FXML
    private Label end;
    @FXML
    private Label size;
    //count of hydra heads
    int count = 0;
    //initializing hydra heads
    Image hydra1 = new Image("file:src/main/resources/HeadSize1.png", 40, 40, false, false);
    Image hydra2 = new Image("file:src/main/resources/HeadSize2.png", 40, 40, false, false);
    Image hydra3 = new Image("file:src/main/resources/HeadSize3.png", 40, 40, false, false);
    Image hydra4 = new Image("file:src/main/resources/HeadSize4.png", 40, 40, false, false);
    Image hydra5 = new Image("file:src/main/resources/HeadSize5.png", 40, 40, false, false);
    Image hydra6 = new Image("file:src/main/resources/HeadSize6.png", 40, 40, false, false);

    ImageView head1 = new ImageView(hydra1);
    ImageView head2 = new ImageView(hydra2);
    ImageView head3 = new ImageView(hydra3);
    ImageView head4 = new ImageView(hydra4);
    ImageView head5 = new ImageView(hydra5);
    ImageView head6 = new ImageView(hydra6);

    //creating imageview and image arrays
    ImageView[] hydras = {head1, head2, head3, head4, head5, head6};
    Image[] hydraIm = {hydra1, hydra2, hydra3, hydra4, hydra5, hydra6};

    //method to extract node from gridpane
    private Node getNodeFromGridPane(GridPane grid, int vert, int hori) {
        for (Node nodeFound : grid.getChildren()) {
            if (nodeFound instanceof ImageView && grid.getColumnIndex(nodeFound) == vert && grid.getRowIndex(nodeFound) == hori) {
                return nodeFound;
            }
        }
        return null;
    }
    //generates random grid node
    private int[] randomizer() {
        Random random = new Random();
        int x = 0;
        int y = 0;
        while (true) {
            x = random.nextInt(17);
            y = random.nextInt(17);
            //prevent overlapping
            ImageView verify = (ImageView) getNodeFromGridPane(grid, x, y);
            if (verify == null) {
                break;
            }
        }
        int[] coords = new int[2];
        coords[0] = x;
        coords[1] = y;
        return coords;

    }

    //play button
    @FXML
    protected void start() {
        int initialSize = (int) slider.getValue();
        slider.setDisable(true);
        play.setDisable(true);
        int[] c1 = randomizer();
        grid.add(getImageView(initialSize), c1[0], c1[1]);


    }

    //reset button
    @FXML
    protected void reset() {
        grid.getChildren().clear();
        slider.setValue(4);
        slider.setDisable(false);
        play.setDisable(false);
        end.setText("");
        end.setDisable(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        size.setText("Size");
    }
    //retrieves imageview from array

    private ImageView getImageView(int size) {
        ImageView imageView = new ImageView(hydraIm[size - 1]);
        return imageView;
    }
    //method for when hydra head is clicked
    @FXML
    protected void click(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode != grid) {
            Node parent = clickedNode.getParent();
            while (parent != grid) {
                clickedNode = parent;
                parent = clickedNode.getParent();
            }
        }

        Integer colIndex = grid.getColumnIndex(clickedNode);
        Integer rowIndex = grid.getRowIndex(clickedNode);
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if (node instanceof ImageView && grid.getRowIndex(node) == rowIndex && grid.getColumnIndex(node) == colIndex) {
                ;
                ImageView imageView = new ImageView();
                imageView = (ImageView) node;
                int size = 0;
                for (int i = 0; i < hydras.length; i++) {
                    if (hydras[i].getImage() == imageView.getImage()) {
                        size = i + 1;
                    }
                }
                grid.getChildren().remove(imageView);
                count++;
                Random rand = new Random();
                if (size != 1) {
                    int heads = rand.nextInt(2) + 2;
                    int[] c1 = new int[2];
                    for (int i = 0; i < heads; i++) {
                        c1 = randomizer();
                        grid.add(getImageView(size - 1), c1[0], c1[1]);
                    }
                } else {
                    deadCheck();
                }
                break;
            }
        }
    }
    //checks if head has been cut
    private void deadCheck(){
        boolean gameOver = true;
        for (int x =0;x<17;x++){
            for (int y=0;y<17;y++){
                if (getNodeFromGridPane(grid,x,y)!=null){
                    gameOver = false;
                    break;
                }
            }
        }

    if(gameOver){
        end.setText("\t\tGood Work!\nYou Have Cut "+count+" Hydra Heads!");
        end.setDisable(false);
    }
}


}
