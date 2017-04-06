package fr.ece.ferminmoreno.finalproject;

public class Match {

    private String title;
    private String player1;
    private String player2;
    private int set1;
    private int set2;
    private int score1;
    private int score2;
    private double latitude;
    private double longitude;

    public Match() {}

    public Match(String title, String player1, String player2, int set1, int set2, int score1,
                 int score2, double latitude, double longitude) {
        this.title = title;
        this.player1 = player1;
        this.player2 = player2;
        this.set1 = set1;
        this.set2 = set2;
        this.score1 = score1;
        this.score2 = score2;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getSet1() {
        return set1;
    }

    public int getSet2() {
        return set2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setPlayer1(String player1){
        this.player1 = player1;
    }

    public void setPlayer2(String player2){
        this.player2 = player2;
    }

    public void setSet1(int set1) {
        this.set1 = set1;
    }

    public void setSet2(int set2) {
        this.set2 = set2;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}