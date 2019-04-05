public class ScoreEntry implements Comparable<ScoreEntry>{
    private String name;
    private int score;

    public ScoreEntry(String name, int score){
        this.name = name;
        this.score = score;
    }

    public ScoreEntry(String rawScore){
        // format of "\"<string>\",<int>"
        try {
            rawScore = rawScore.trim();
            this.name = rawScore.split(",")[0].replace("\"","");
            this.score = Integer.valueOf(rawScore.split(",")[1].replaceAll("\\D",""));

        }catch (Exception e){
            e.printStackTrace();
            this.name = null;
            this.score = 0;
        }

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoreEntry other){
        if (this.getScore() < other.getScore()){
            return -1;
        }
        else if(this.getScore() > other.getScore()){
            return 1;
        }
        else {
            return  0;
        }
    }

    @Override
    public String toString(){
        return String.format("\"%s\",%d;", this.name, this.score);
    }
}