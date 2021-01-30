import java.util.Vector;

public class Check_Solvable {

    int [][] first;
    int [][] second;

    Vector<Integer> list1;
    Vector<Integer> list2;

    public Check_Solvable(){
        first=new int[4][4];
        second=new int[4][4];
        list1=new Vector<>();
        list2=new Vector<>();
    }

    public void setFirst(int[][] first) {
        this.first = first;

        /*for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                System.out.println(first[i][j]);
            }
        }*/
    }

    public void setSecond(int[][] second) {
        this.second = second;
    }

    public void setList1(Vector<Integer> list1) {
        this.list1 = list1;

        //for(int i=0;i<list1.size();i++) System.out.println(list1.get(i));
    }

    public void setList2(Vector<Integer> list2) {
        this.list2 = list2;
    }

    public int count_inversion(Vector<Integer> v)
    {
        int count=0;
        for (int i=0;i<v.size();i++)
        {
            int x=v.get(i);
            if(x!=0)
            {
                for(int j=i+1;j<v.size();j++)
                {
                    int y=v.get(j);
                    if(y!=0 && x>y) count++;

                }
            }

        }
        return count;
    }

    public int search_empty_space_row_no(int[][] a)
    {
        int row=0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(a[i][j]==0)
                {
                    row=i;
                    return row;
                }
            }
        }
        return row;
    }

    public boolean solvable_or_not()
    {
        boolean flag=false;

        int in_inv_count=count_inversion(list1);
        //System.out.println(in_inv_count);
        int fi_inv_count=count_inversion(list2);
        //System.out.println(fi_inv_count);

        int in_zero_height=4-search_empty_space_row_no(first);
       // System.out.println(in_zero_height);
        int fi_zero_height=4-search_empty_space_row_no(second);
        //System.out.println(fi_zero_height);

        /*if((in_inv_count%2==0 && in_zero_height%2!=0) &&(fi_inv_count%2==0 && fi_zero_height%2!=0)) flag=true;

        else if((in_inv_count%2!=0 && in_zero_height%2==0) &&(fi_inv_count%2==0 && fi_zero_height%2!=0)) flag=true;

        else if((in_inv_count%2==0 && in_zero_height%2!=0) &&(fi_inv_count%2!=0 && fi_zero_height%2==0)) flag=true;

        else if((in_inv_count%2!=0 && in_zero_height%2==0) &&(fi_inv_count%2!=0 && fi_zero_height%2==0)) flag=true;*/

        /*if((in_inv_count+in_zero_height)%2==0 && (fi_inv_count+fi_zero_height)%2==0) flag=true;

        else if((in_inv_count+in_zero_height)%2!=0 && (fi_inv_count+fi_zero_height)%2!=0) flag=true;

        else flag=false;*/

        if((in_inv_count%2==0 && in_zero_height%2!=0) ) flag=true;

        else if((in_inv_count%2!=0 && in_zero_height%2==0) ) flag=true;

        else flag=false;


        return flag;
    }

}
