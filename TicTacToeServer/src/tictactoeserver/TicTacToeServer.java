/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.*;
import java.net.*;
public class TicTacToeServer {

    public static BufferedReader inFromClient[] = new BufferedReader[2];
    public static DataOutputStream outToClient[] = new DataOutputStream[2];
    public static int count=0;
    public static void main(String[] args) throws Exception{
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6782);
        System.out.println(welcomeSocket.isClosed());
        Socket connectionSocket[] = new Socket[2];
        
        //while (true)
        for (int i = 0; i < 2; i++) {
            System.out.println("waiting\n ");
            connectionSocket[i] = welcomeSocket.accept();
            System.out.println("connected "+i);
            inFromClient[i] =
            new BufferedReader(new InputStreamReader(
            connectionSocket[i].getInputStream()));
            outToClient[i] =
            new DataOutputStream(
            connectionSocket[i].getOutputStream());
            outToClient[i].writeBytes(String.valueOf(i)+'\n');
        }
        SThread A2B = new SThread(inFromClient[0], outToClient[1], 0);
        SThread B2A = new SThread(inFromClient[1], outToClient[0], 1);
        A2B.join();
        B2A.join();
    }
}
//===========================================================
class SThread extends Thread {
BufferedReader inFromClient;
DataOutputStream outToClient;
String clientSentence;
int srcid;

int go = 0;                                  /* Square selection number for turn     */
int row = 0;                                 /* Row index for a square               */
int column = 0;                              /* Column index for a square            */
int line = 0;                                /* Row or column index in checking loop */
int winner = 10;                              /* The winning player                   */
char[][] board = {                         /* The board                            */
                   {'1','2','3'},          /* Initial values are reference numbers */
                   {'4','5','6'},          /* used to select a vacant square for   */
                   {'7','8','9'}           /* a turn.                              */
                 };


public SThread(BufferedReader in, DataOutputStream out, int a) {
inFromClient = in;
outToClient = out;
srcid = a;
start();
}
public void run() 
{
    while(true) 
    {
        try {
            clientSentence = inFromClient.readLine();
            
            
            TicTacToeServer.count++;
            if(TicTacToeServer.count>=9)
            {
                outToClient.writeBytes("How boring, it is a draw"+'\n');
            }
            else
            {
            
            if(srcid==0)
            {
                if(clientSentence.equals("Player 1 winner"))
                {
                    outToClient.writeBytes("Congratulations, YOU ARE THE WINNER!"+'\n');
                    
                }
                else
                {
                go=Integer.parseInt(clientSentence);
                row = --go/3;                                 /* Get row index of square      */
                column = go%3;
                board[row][column] ='X';
                if((board[0][0] == board[1][1] && board[0][0] == board[2][2]) ||(board[0][2] == board[1][1] && board[0][2] == board[2][0]))
                {
                    winner=srcid;
                }
                else
                {
                    for(line = 0; line <= 2; line ++)
                    {
                        if((board[line][0] == board[line][1] && board[line][0] == board[line][2])||(board[0][line] == board[1][line] && board[0][line] == board[2][line]))
                        {
                            winner=srcid;
                        }
                    }
                }
                if(winner==10)
                {
                    outToClient.writeBytes(clientSentence + '\n');
                }

                else
                {
                    outToClient.writeBytes("Player 0 winner"+ '\n');
                    
                }
                }
            }
            else
            {
                if(clientSentence.equals("Player 0 winner"))
                {
                    outToClient.writeBytes("Congratulations, YOU ARE THE WINNER!"+'\n');
                    
                }
                else
                {
                go=Integer.parseInt(clientSentence);
                row = --go/3;                                 /* Get row index of square      */
                column = go%3;
                board[row][column] ='O';
                
                if((board[0][0] == board[1][1] && board[0][0] == board[2][2]) ||(board[0][2] == board[1][1] && board[0][2] == board[2][0]))
                {
                    winner=srcid;
                }
                
                else
                {
                    for(line = 0; line <= 2; line ++)
                    {
                        if((board[line][0] == board[line][1] && board[line][0] == board[line][2])||(board[0][line] == board[1][line] && board[0][line] == board[2][line]))
                        {
                            winner=srcid;
                        }
                    }
                }
                if(winner==10)
                {
                    outToClient.writeBytes(clientSentence + '\n');
                }
                else
                {
                    outToClient.writeBytes("Player 1 winner"+ '\n');
                    
                }
                
                } 
            }
            
            }
            } catch (Exception ex) {
            }
    }
    
  }
}