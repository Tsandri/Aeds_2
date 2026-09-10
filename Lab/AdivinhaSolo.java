import java.util.Scanner;

class AdivinhaSolo{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()){
            int n = sc.nextInt();
                guess(sc, n);
        }
        sc.close();
    }
    public static void guess(Scanner sc, int n){
        int[] stack = new int[1005], queue = new int[1005], pQueue = new int[1005];
        boolean isStack = true, isQueue = true, isPQ = true;
        int topStack = 0, inicioQ = 0, fimQ = 0, lenPQ = 0;
        
        for(int i = 0; i < n; i++){
            int comand = sc.nextInt();
            int x = sc.nextInt();

            if(comand == 1){
                stack[topStack] = x;
                topStack++;
                queue[fimQ] = x;
                fimQ++;
                pQueue[lenPQ] = x;
                lenPQ++;
            }
            else{
                if(isStack){
                    if(topStack == 0 || stack[topStack - 1] != x){
                        isStack = false;
                    }
                    else{
                        topStack--;
                    }
                }
                if(isQueue){
                    if(inicioQ == fimQ || queue[inicioQ] != x){
                        isQueue = false;
                    }
                    else{
                        inicioQ++;
                    }
                }
                if(isPQ){
                    if(lenPQ == 0){
                        isPQ = false;
                    }
                    else{
                        int maiorInd = 0;
                        for(int j = 0; j < lenPQ; j++){
                            if(pQueue[j] > pQueue[maiorInd]){
                                maiorInd = j;
                            }
                        }
                        if(pQueue[maiorInd] != x){
                            isPQ = false;
                        }
                        else{
                            pQueue[maiorInd] = pQueue[lenPQ - 1];
                            lenPQ--;
                        }
                    }
                }
            }
         }
        int verdades = 0;
        if(isStack){
            verdades++;
        }
        if(isQueue){
            verdades++;
        }
        if(isPQ){
            verdades++;
        }
        if(verdades == 0){
            System.out.println("impossible");
        }
        else if(verdades > 1){
            System.out.println("not sure");
        }
        else{
            if(isStack){
                System.out.println("stack");
            }
            if(isQueue){
                System.out.println("queue");
            }
            if(isPQ){
                System.out.println("priority queue");
            }
        }
    }
}
