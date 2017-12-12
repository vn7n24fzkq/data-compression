public class ScalarQuantisation {
    static int[][] matrix =
            {{139,144,149,153,155,155,155,155},
            {144,151,153,156,159,156,156,156},
            {150,155,160,163,158,156,156,156},
            {159,161,162,160,160,159,159,159},
            {159,160,161,162,162,155,155,155},
            {161,161,161,161,160,157,157,157},
            {162,162,161,163,162,157,157,157},
            {162,162,161,161,163,158,158,158}};
    int level = 8;
    int min,max;
    int[] boundaries;
    int[] codebook;
    public static void main(String[] args){

        ScalarQuantisation sq =  new ScalarQuantisation();
        int array[][] = sq.getUniformedLevelMatrix(matrix);
        for(int i = 0; i < array.length;i++){
            for(int j = 0;j < array[i].length;j++){
               System.out.print(array[i][j]+",");
            }
            System.out.println();
        }
        array = sq.getUniformedQuantizedMatrix(array);
        for(int i = 0; i < array.length;i++){
            for(int j = 0;j < array[i].length;j++){
                System.out.print(array[i][j]+",");
            }
            System.out.println();
        }
    }

    private  void getBoundary(int[][] array){
        min = array[0][0];
        max = array[0][0];
            for(int i = 0;i < array.length;i++){
                for(int j = 0;j < array[i].length;j++){
                    if(array[i][j]>max) max = array[i][j];
                    if(array[i][j]<min) min = array[i][j];
                }
            }
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int[][] getUniformedLevelMatrix(int[][] array){
        int[][]  quantizedArray = array;
        getBoundary(array);
        boundaries = new int[level];
        codebook = new int[level];
        int temp = (max-min)/level;
        for(int i = 0 ;i < level;i++){
            boundaries[i] = min + (i+1)*temp;
            codebook[i] = boundaries[i]-(temp+1)/2;
        }
        for(int i = 0; i < array.length;i++){
            for(int j = 0;j < array[i].length;j++){
                quantizedArray[i][j] = getUniformedLevel(array[i][j]);
            }
        }
        return  quantizedArray;
    }

    public int getUniformedLevel(int data){
        int level;
        for(level = 0;level < boundaries.length;level++){
            if(data <= boundaries[level]) break;
        }
        return  level;
    }
    public int[][] getUniformedQuantizedMatrix(int[][] data){
        int[][] array = data;
        for(int i = 0; i < data.length;i++){
            for(int j = 0;j < data[i].length;j++){
                array[i][j] = getQuantizedValue(array[i][j]);
            }
        }
        return array;
    }
    public int getQuantizedValue(int level){
        return  codebook[level];
    }
}
