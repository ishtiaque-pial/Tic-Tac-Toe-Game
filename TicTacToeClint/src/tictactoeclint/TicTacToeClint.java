/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclint;

import java.io.*;
import java.net.*;
public class TicTacToeClint {

    public static String modifiedSentence;
    public static void main(String[] args) throws Exception{
       

        InetAddress inetAddress = InetAddress.getLocalHost();
        //.getByName(String hostname); "CL11"
        System.out.println(inetAddress);

        Socket clientSocket = new Socket(inetAddress, 6782);
        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        modifiedSentence=inFromServer.readLine();
        System.out.println("Player"+modifiedSentence);
        
        CThread write = new CThread(inFromServer, outToServer, Integer.parseInt(modifiedSentence));
        

        write.join();
        clientSocket.close();
    }
    
}

class CThread extends Thread {

    BufferedReader inFromServer;
    DataOutputStream outToServer;
    int RW_Flag;
    String player1Poisition,player2Poisition;
    int player = 0;                              /* Player number - 1 or 2               */
    int go = 0;                                  /* Square selection number for turn     */
    int row = 0;                                 /* Row index for a square               */
    int column = 0;                              /* Column index for a square            */
    int line = 0;                                /* Row or column index in checking loop */
    int winner = 0;                              /* The winning player                   */
    char[][] board = {                         /* The board                            */
                       {'1','2','3'},          /* Initial values are reference numbers */
                       {'4','5','6'},          /* used to select a vacant square for   */
                       {'7','8','9'}           /* a turn.                              */
                     };

    public CThread(BufferedReader in, DataOutputStream out, int rwFlag) {
        inFromServer = in;
        outToServer = out;
        RW_Flag = rwFlag;
        
        int i = 0;                                   /* Loop counter                         */
        start();
    }

    public void run() {
        try
        {
            while (true) 
            {                
                if(RW_Flag==0)
                {
                    BufferedReader inFromUser1 =new BufferedReader(new InputStreamReader(System.in));
                    // System.out.println("Enter your number");
                    do
                    {
                        System.out.println("\n");
                        System.out.println(" "+board[0][0]+" | "+board[0][1]+" | "+board[0][2]);
                        System.out.println("---+---+---");
                        System.out.println(" "+board[1][0]+" | "+board[1][1]+" | "+board[1][2]);
                        System.out.println("---+---+---");
                        System.out.println(" "+board[2][0]+" | "+board[2][1]+" | "+board[2][2]);
                        System.out.println("Player "+RW_Flag+",please enter the number of the square where you want to place your X");
                        player1Poisition=inFromUser1.readLine();
                        go=Integer.parseInt(player1Poisition);
                        row = --go/3;                                 /* Get row index of square      */
                        column = go%3;
                    }while(go<0 || go>9 || board[row][column]>'9');
                
                    board[row][column] ='X';
                    
                    System.out.println(" "+board[0][0]+" | "+board[0][1]+" | "+board[0][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[1][0]+" | "+board[1][1]+" | "+board[1][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[2][0]+" | "+board[2][1]+" | "+board[2][2]);
                    outToServer.writeBytes(player1Poisition+'\n');
                    System.out.println("Plz wait");
                    player2Poisition=inFromServer.readLine();
                    
                    if(player2Poisition.equals("How boring, it is a draw")||player2Poisition.equals("Congratulations, YOU ARE THE WINNER!")||player2Poisition.equals("Player 1 winner"))
                    {
                        outToServer.writeBytes(player2Poisition+'\n');
                        System.out.println(player2Poisition);
                        break;
                    }
                    else
                    {
                    go=Integer.parseInt(player2Poisition);
                    row = --go/3;                                 /* Get row index of square      */
                    column = go%3;
                    board[row][column] ='O';
                    System.out.println(" "+board[0][0]+" | "+board[0][1]+" | "+board[0][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[1][0]+" | "+board[1][1]+" | "+board[1][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[2][0]+" | "+board[2][1]+" | "+board[2][2]);
                    go=0;
                    }
                }
                else
                {
                    BufferedReader inFromUser2 =new BufferedReader(new InputStreamReader(System.in));
                    System.out.println(" "+board[0][0]+" | "+board[0][1]+" | "+board[0][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[1][0]+" | "+board[1][1]+" | "+board[1][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[2][0]+" | "+board[2][1]+" | "+board[2][2]);
                    System.out.println("Plz wait");
                    System.out.println("\n");
                    player1Poisition=inFromServer.readLine();
                    if(player1Poisition.equals("How boring, it is a draw")||player1Poisition.equals("Congratulations, YOU ARE THE WINNER!")||player1Poisition.equals("Player 0 winner"))
                    {
                        outToServer.writeBytes(player1Poisition+'\n');
                        System.out.println(player1Poisition);
                        break;
                    }
                    else
                    {
                    go=Integer.parseInt(player1Poisition);
                    row = --go/3;                                 /* Get row index of square      */
                    column = go%3;
                    board[row][column] ='X';
                    
                    go=0;
                    do
                    {
                        System.out.println(" "+board[0][0]+" | "+board[0][1]+" | "+board[0][2]);
                        System.out.println("---+---+---");
                        System.out.println(" "+board[1][0]+" | "+board[1][1]+" | "+board[1][2]);
                        System.out.println("---+---+---");
                        System.out.println(" "+board[2][0]+" | "+board[2][1]+" | "+board[2][2]);
                        System.out.println("Player "+RW_Flag+",please enter the number of the square where you want to place your O");
                        player2Poisition=inFromUser2.readLine();
                        go=Integer.parseInt(player2Poisition);
                        row = --go/3;                                 /* Get row index of square      */
                        column = go%3;
                    }while(go<0 || go>9 || board[row][column]>'9');
                    board[row][column] ='O';
                    System.out.println(" "+board[0][0]+" | "+board[0][1]+" | "+board[0][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[1][0]+" | "+board[1][1]+" | "+board[1][2]);
                    System.out.println("---+---+---");
                    System.out.println(" "+board[2][0]+" | "+board[2][1]+" | "+board[2][2]);
                    outToServer.writeBytes(player2Poisition+'\n');
                }
                System.out.println("\n");
            }
            }
        }
        catch(Exception ex)
        {
            
        }
    }
}

