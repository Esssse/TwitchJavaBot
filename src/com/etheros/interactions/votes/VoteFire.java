package com.etheros.interactions.votes;

public class VoteFire implements Runnable {

    private boolean vote = false;

    private int voteY = 0;
    private int voteN = 0;

    private String command;

    void VoteFire(String command){
        this.command = command;
    }

    @Override
    public void run() {
            if (command.equalsIgnoreCase("yes"))
                voteY++;
            else if (command.equalsIgnoreCase("no")){
                voteN++;
            }
    }
}
