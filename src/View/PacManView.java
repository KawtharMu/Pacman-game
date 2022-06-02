package View;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Controller.Controller;
import Model.Game;
import Utils.CellValue;

public class PacManView extends Group {
    public final static double CELL_WIDTH = 20.0;

    @FXML private int rowCount;
    @FXML private int columnCount;
    private ImageView[][] cellViews;
    private Image pacmanRightImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image[] ghostsImages = new Image[3];
    private Image bluePacmanRightImage;
    private Image bluePacmanUpImage;
    private Image bluePacmanLeftImage;
    private Image bluePacmanDownImage;
    private Image blueGhostImage;
    private Image wallImage;
    private Image bigDotImage;
    private Image smallDotImage;
    private Image easyQuestion;
    private Image mediumQuestion;
    private Image hardQuestion;

    /**
     * Initializes the values of the image instance variables from files
     */
    public PacManView() {
        this.pacmanRightImage = new Image(getClass().getResourceAsStream("/images/pacmanRight.gif"));
        this.pacmanUpImage = new Image(getClass().getResourceAsStream("/images/pacmanUp.gif"));
        this.pacmanDownImage = new Image(getClass().getResourceAsStream("/images/pacmanDown.gif"));
        this.pacmanLeftImage = new Image(getClass().getResourceAsStream("/images/pacmanLeft.gif"));
        this.bluePacmanRightImage = new Image(getClass().getResourceAsStream("/images/bluePacmanRight.gif"));
        this.bluePacmanUpImage = new Image(getClass().getResourceAsStream("/images/bluePacmanUp.gif"));
        this.bluePacmanLeftImage = new Image(getClass().getResourceAsStream("/images/bluePacmanLeft.gif"));
        this.bluePacmanDownImage = new Image(getClass().getResourceAsStream("/images/bluePacmanDown.gif"));
        this.ghostsImages[0] = new Image(getClass().getResourceAsStream("/images/redghost.gif"));
        this.ghostsImages[1] = new Image(getClass().getResourceAsStream("/images/ghost2.gif"));
        this.ghostsImages[2] = new Image(getClass().getResourceAsStream("/images/pinkghost.gif"));
        this.blueGhostImage = new Image(getClass().getResourceAsStream("/images/blueghost.gif"));
        this.wallImage = new Image(getClass().getResourceAsStream("/images/wall.png"));
        this.smallDotImage = new Image(getClass().getResourceAsStream("/images/smalldot.png"));
        this.bigDotImage = new Image(getClass().getResourceAsStream("/images/BigDot.png"));
        this.easyQuestion = new Image(getClass().getResourceAsStream("/images/easyQuest.png"));
        this.mediumQuestion = new Image(getClass().getResourceAsStream("/images/mediumQuest.png"));
        this.hardQuestion = new Image(getClass().getResourceAsStream("/images/hardQuest.png"));
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * CELL_WIDTH);
                    imageView.setY((double)row * CELL_WIDTH);
                    imageView.setFitWidth(CELL_WIDTH);
                    imageView.setFitHeight(CELL_WIDTH);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    /** Updates the view to reflect the state of the model
     *
     * 
     */
    public void update(Game model) {
        assert model.getRowCount() == this.rowCount && model.getColumnCount() == this.columnCount;
        //for each ImageView, set the image to correspond with the CellValue of that cell
        for (int row = 0; row < this.rowCount; row++){
            for (int column = 0; column < this.columnCount; column++){
                CellValue value = model.getCellValue(row, column);
                if (value == CellValue.WALL) {
                    this.cellViews[row][column].setImage(this.wallImage);
                }
                else if (value == CellValue.BIGDOT) {
                    this.cellViews[row][column].setImage(this.bigDotImage);
                }
                else if (value == CellValue.SMALLDOT) {
                    this.cellViews[row][column].setImage(this.smallDotImage);
                }
                else if (value == CellValue.EQ) {
                    this.cellViews[row][column].setImage(this.easyQuestion);
                }
                else if (value == CellValue.MQ) {
                    this.cellViews[row][column].setImage(this.mediumQuestion);
                }
                else if (value == CellValue.HQ) {
                    this.cellViews[row][column].setImage(this.hardQuestion);
                }
                else {
                    this.cellViews[row][column].setImage(null);
                }
                //check which direction PacMan is going in and display the corresponding image
                if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && (model.getLastDirection() == Utils.Direction.RIGHT || model.getLastDirection() == Utils.Direction.NONE)) {
                    this.cellViews[row][column].setImage(this.pacmanRightImage);
                }
                else if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && model.getLastDirection() == Utils.Direction.LEFT) {
                    this.cellViews[row][column].setImage(this.pacmanLeftImage);
                }
                else if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && model.getLastDirection() == Utils.Direction.UP) {
                    this.cellViews[row][column].setImage(this.pacmanUpImage);
                }
                else if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && model.getLastDirection() == Utils.Direction.DOWN) {
                    this.cellViews[row][column].setImage(this.pacmanDownImage);
                }
               
              //make pacman in blue color if eating bombDot the is the Memento
                if (model.getInstance().getCountOfBombDots() > 0) {
                	if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && (model.getLastDirection() == Utils.Direction.LEFT ))
                			this.cellViews[row][column].setImage(this.bluePacmanLeftImage);
                }
                if (model.getInstance().getCountOfBombDots() > 0) {
                	if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && (model.getLastDirection() == Utils.Direction.UP ))
                			this.cellViews[row][column].setImage(this.bluePacmanUpImage);
                }
                if (model.getInstance().getCountOfBombDots() > 0) {
                	if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && (model.getLastDirection() == Utils.Direction.RIGHT || model.getLastDirection() == Utils.Direction.NONE))
                			this.cellViews[row][column].setImage(this.bluePacmanRightImage);
                }
                if (model.getInstance().getCountOfBombDots() > 0) {
                	if (row == model.getPacman().getPacmanLocation().getX() && column == model.getPacman().getPacmanLocation().getY() && (model.getLastDirection() == Utils.Direction.DOWN ))
                			this.cellViews[row][column].setImage(this.bluePacmanDownImage);
                }
                for(int i = 0; i < model.getInstance().getGhosts().length; i++)
                {
                	if (model.getGhosts()[i].getGhostLocation()!=null && row == model.getGhosts()[i].getGhostLocation().getX() && column == model.getGhosts()[i].getGhostLocation().getY()) {
                        this.cellViews[row][column].setImage(this.ghostsImages[i]);
                    }
                }
            }
        }
    }
    

    //Getters and Setters
    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.initializeGrid();
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.initializeGrid();
    }
}
