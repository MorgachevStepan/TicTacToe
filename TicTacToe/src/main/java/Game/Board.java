package Game;

import java.util.Arrays;
import java.util.Scanner;

import Exception.MissTurnException;

public class Board {

    final private byte SIZEX = 3;
    final private byte SIZEY = 3;
    private final char[][] gameField;
    private byte movesCounter;
    private char winPlayer;
    private final byte[] movesMask;

    public Board(){
        gameField = new char[SIZEX][SIZEY];
        for(int i = 0; i < SIZEX; i++)
            for(int j = 0; j < SIZEY; j++)
                gameField[i][j] = ' ';
        movesCounter = 0;
        winPlayer = 'N';
        movesMask = new byte[9];
    }

    public String showGameField(){
        StringBuilder resultString = new StringBuilder();
        resultString.append("-------\n");

        for(int i = 0; i < 3; i++){
            resultString.append("|");
            for(int j = 0; j < 3; j++){
                resultString.append(gameField[i][j]);
                resultString.append("|");
            }
            resultString.append("\n");
            resultString.append("-------\n");
        }

        return resultString.toString();
    }

    public void playersMove(int position) throws MissTurnException {
        boolean flag = false;
        int column = (position - 1) / 3;
        int row = (position - 1) % 3;
        if(isClear(position)){
            gameField[column][row] = 'X';
            movesMask[position - 1] = 1;
            movesCounter++;
            flag = true;
        }
        if(!flag)
            throw new MissTurnException("");
    }

    public boolean isGameEnd(){
        char[] fullX = {'X', 'X', 'X'};
        char[] fullO = {'O', 'O', 'O'};

        char[] colFirst = {gameField[0][0], gameField[1][0], gameField[2][0]};
        char[] colSecond = {gameField[0][1], gameField[1][1], gameField[2][1]};
        char[] colThird = {gameField[0][2], gameField[1][2], gameField[2][2]};

        char[] mainDiagonal ={gameField[0][0], gameField[1][1], gameField[2][2]};
        char[] secondDiagonal = {gameField[0][2], gameField[1][1], gameField[2][0]};

        if(Arrays.equals(gameField[0], fullX) || Arrays.equals(gameField[1], fullX) || Arrays.equals(gameField[2], fullX)
           || Arrays.equals(colFirst, fullX) || Arrays.equals(colSecond, fullX) || Arrays.equals(colThird, fullX)
           || Arrays.equals(mainDiagonal, fullX) || Arrays.equals(secondDiagonal, fullX)) {
            winPlayer = 'X';
            return true;
        }
        if(Arrays.equals(gameField[0], fullO) || Arrays.equals(gameField[1], fullO) || Arrays.equals(gameField[2], fullO)
                || Arrays.equals(colFirst, fullO) || Arrays.equals(colSecond, fullO) || Arrays.equals(colThird, fullO)
                || Arrays.equals(mainDiagonal, fullO) || Arrays.equals(secondDiagonal, fullO)) {
            winPlayer = 'O';
            return true;
        }
        return movesCounter == 9;
    }

    public void computerMove() {
        while(true){
            int currentMove = generateMove();
            if(isClear(currentMove)) {
                int column = (currentMove) / 3;
                int row = (currentMove) % 3;
                gameField[column][row] = 'O';
                movesMask[currentMove] = 1;
                return;
            }
        }
    }

    public void viewResults() {
        switch (winPlayer) {
            case 'X' -> System.out.println("Вы выиграли!");
            case 'O' -> System.out.println("Компьютер выиграл!");
            default -> System.out.println("Ничья!");
        }
    }

    public boolean logicPlayersMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ваш ход");
        if(scanner.hasNextInt()){
            int player = scanner.nextInt();
            try {
                this.playersMove(player);
            } catch (MissTurnException e) {
                System.out.println("Неверный ход! Попробуйте снова");
                return false;
            }
        }
        else{
            System.out.println("Неправильный ввод! Попробуйте снова");
            return false;
        }
        System.out.println(this.showGameField());
        return true;
    }

    public void showComputersMove() {
        System.out.println("Ход компьютера");
        this.computerMove();
        System.out.printf(this.showGameField());
    }

    public void runGame() {
        boolean flagTurn = true;
        System.out.println(this.showGameField());
        while(!this.isGameEnd()) {
            if(flagTurn){
                if(!this.logicPlayersMove()){
                    continue;
                }
                flagTurn = false;
            }
            else{
                this.showComputersMove();
                flagTurn = true;
            }
        }
        this.viewResults();
    }

    private int generateMove() {
        return (int)(Math.random() * 9);
    }

    private boolean isClear(int position) {
        return movesMask[position - 1] == 0;
    }

}
