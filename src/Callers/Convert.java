package Callers;
/*
 @name: Convert
 @author: Liam Gardner
 @Date: one of our classes. I think the one on Tuesday but I can't remember.
 */
public class Convert {
//this entire object just converts strings to 3d integer arrays and backwards. This is how I sent a board accross. there's no need for indiviudal comenting. it's 11:59
//I've been doing this faster than I thought... neat
    int[][][] i;
    String o;


    public Convert(int[][][] toConvert){
        this.i = toConvert;
        this.o = ConvertToObject();
    }

    public Convert(String fromConvert){
        this.o = fromConvert;
        this.i = ConvertFromObject();
    }

    public String ConvertToObject(){
        String a = "";
        for (int dep = 0; dep < 5; dep++){
            for (int row = 0; row < 5; row++){
                for (int col = 0; col < 5; col++){
                    a += this.i[dep][row][col] + " ";
                }
            }
        }
        a = a.substring(0,a.length()-1);
        return a;
    }

    public int[] ConvertToSingleArray(String a){
        int[] k = new int[125];
        int counter = 0;
        for (String b : a.split(" ")){
            k[counter] = Integer.parseInt(b);
            counter++;
        }
        return k;
    }

    public int[][][] ConvertFromObject(){
        int[] first = ConvertToSingleArray(this.o);
        int[][][] toReturn = new int[5][5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                for (int k = 0; k < 5; k++){
                    toReturn[i][j][k] = first[5*5*i + 5*j + k];
                }
            }
        }
        return toReturn;
    }

}
