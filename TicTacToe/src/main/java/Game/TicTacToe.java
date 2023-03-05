package Game;

/*
Простая реализация игры в крестики нолики.
1. Вы всегда ходите первым
2. Для выбора хода нужно нажать на цифру от 1 до 9,
где цифры - номера элементов массива + 1
3. Компьютер ходит всегда случайно

 */

public class TicTacToe {
    public static void main(String[] args) {
        Board board = new Board();
        board.runGame();
    }
}