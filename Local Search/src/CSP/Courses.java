package CSP;

import java.io.*;
import java.util.*;

public class Courses {

    private int nodes;
    private int students;
    private HashSet<Integer> adjList[];
    //private PriorityQueue<Integer> list_of_degree;
    private int[][] list_of_degree2;
    private int largest_degree_result[];
    private int max_timeslot_largest_degree=-1;
    private int max_timeslot_dsat=-1;


    private PriorityQueue<Vertex> vertex_dsat;
    private Vertex access_sat[];
    private Set<Integer> found;


    private HashSet<Integer> each_course_of_students[];
    private float penalty_largest_degree;
    private float penalty_dsatur;
    private float prev_penalty_largest_degree;
    private float prev_penalty_dsatur;

    //private ArrayList<Integer> saturation_degree_list;
    private int largest_degree_copy[];
    private int dsatur_copy[];
    private HashSet<Integer> kempe_chain_elements;

    Courses(int v)
    {
        nodes=v;
        adjList= new HashSet[nodes+1];
        for(int i=0;i<nodes+1;i++)
        {
            adjList[i]=new HashSet<>();
        }

        //list_of_degree=new PriorityQueue<>(Collections.reverseOrder());
        list_of_degree2=new int[nodes][2];
        largest_degree_result= new int[nodes+1];
        Arrays.fill(largest_degree_result,-1);

        vertex_dsat=new PriorityQueue<>();
        access_sat= new Vertex[nodes+1];
        found=new HashSet<>();
    }
    public int getNodes() {
        return nodes;
    }

    public void setStudents(int a)
    {
        students=a;
        each_course_of_students=new HashSet[students];
        for(int i=0;i<students;i++)
        {
            each_course_of_students[i]=new HashSet<>();
        }
    }
    public void set_courses(ArrayList<Integer> alist, int s, int c)
    {
        for(int i=0;i<s;i++)
        {
            each_course_of_students[c].add(alist.get(i));
        }
    }
    public void print_courses()
    {
        for(int i=0;i<students;i++)
        {
            Iterator<Integer> it=each_course_of_students[i].iterator();
            while (it.hasNext())
            {
                System.out.print(it.next()+" ");
            }
            System.out.println();
        }
    }
    public void setEdges(int a, int b)
    {
        adjList[a].add(b);
        adjList[b].add(a);
    }
    public void constructGraph(ArrayList<Integer> alist, int s)
    {
        for(int i=0;i<s;i++)
        {
            for(int j=i+1;j<s;j++)
            {
                //if(i!=j)
                //{
                int a=alist.get(i);
                int b=alist.get(j);
                    //adjList[a-1].add(b-1);
                    //adjList[b-1].add(a-1);
                setEdges(a,b);
                //}
            }
        }
    }
    public void print()
    {
        for(int i=1;i<nodes+1;i++)
        {
            System.out.println(adjList[i]);
        }
    }
    public void setList_of_degree()
    {
        for(int i=0;i<nodes;i++)
        {
            //list_of_degree.add(adjList[i].size());
            list_of_degree2[i][0]=adjList[i+1].size();
            list_of_degree2[i][1]=i+1;
        }
        Arrays.sort(list_of_degree2, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });
        /*System.out.println("Node  Degree");
        for(int i=0;i<nodes;i++)
        {
            System.out.println(list_of_degree2[i][1]+" "+list_of_degree2[i][0]);
        }*/
    }
    public void time_scheduling_using_largest_degree()
    {
        setList_of_degree();
        //int u=list_of_degree.poll();
        int u=list_of_degree2[nodes-1][1];
        largest_degree_result[u]=1;
        //System.out.println("Node  Color");
        //System.out.println(u+" "+1);

        boolean colors_available[]=new boolean[nodes+1];
        Arrays.fill(colors_available,true);

        int i=nodes-2;
        while (i>=0)
        {
            int a=list_of_degree2[i][1];
            Iterator<Integer> it= adjList[a].iterator();
            while (it.hasNext())
            {
                int b=it.next();
                if(largest_degree_result[b]!=-1)
                {
                    colors_available[largest_degree_result[b]]=false;
                }
            }
            int color;
            for(color=1;color<nodes+1;color++)
            {
                if(colors_available[color])
                {
                    break;
                }
            }
            largest_degree_result[a]=color;

            Arrays.fill(colors_available,true);
            i--;
            //System.out.println(a+" "+color);
        }
    }

    public void print_after_largest_degree()
    {
        for(int i=1;i<nodes+1;i++)
        {
            if(largest_degree_result[i]>max_timeslot_largest_degree)
            {
                max_timeslot_largest_degree=largest_degree_result[i];
            }
            //System.out.println(i+" "+largest_degree_result[i]);
        }
       // System.out.println(max_timeslot_largest_degree);

    }

    public void set_vertex_list_dsat()
    {
        for (int i=1;i<nodes+1;i++)
        {
            Vertex v=new Vertex();
            v.node_no=i;
            v.node_degree=adjList[i].size();
            v.node_color=0;
            v.node_sat_degree=0;

            Iterator<Integer> it=adjList[i].iterator();
            while (it.hasNext())
            {
                int x=it.next();
                //Vertex v2=new Vertex();
                //v2.node_no=x;
                //v2.node_degree=adjList[x].size();
                v.setNeighbours(x);
            }
            vertex_dsat.add(v);
            access_sat[i]=v;
        }
        /*Iterator<Vertex> it=vertex_dsat.iterator();
        while (it.hasNext())
        {
            Vertex v=it.next();
            System.out.println(v.node_no+" "+v.node_degree+" "+v.node_color+" "+v.node_sat_degree);
        }*/

    }

    public int set_saturation_degree(Vertex v)
    {
        int v_no=v.node_no;
        HashSet<Integer> saturation_degree_list=new HashSet<>();
        ArrayList<Integer> list_of_neighbours=new ArrayList<>();

        Iterator<Integer> it=adjList[v_no].iterator();
        while (it.hasNext())
        {
            list_of_neighbours.add(it.next());
        }
        Iterator<Integer> it2=list_of_neighbours.iterator();
        while (it2.hasNext())
        {
            saturation_degree_list.add(access_sat[it2.next()].node_color);
        }
        if(saturation_degree_list.contains(0))
        {
            return saturation_degree_list.size()-1;
        }
        else
            return saturation_degree_list.size();
    }

    public void DSat_algorithm()
    {
        set_vertex_list_dsat();
        Vertex u=vertex_dsat.poll();
        int u_no=u.node_no;
       // System.out.println(u_no);
        access_sat[u_no].node_color=1;
        found.add(u_no);

        Iterator<Integer> it=adjList[u_no].iterator();
        while (it.hasNext())
        {
            int x=it.next();
            if(!found.contains(x))
            {
                Vertex v=new Vertex();
                v.node_no=x;
                v.node_degree=access_sat[x].node_degree;
               // v.node_sat_degree=access_sat[x].node_sat_degree+1;
                v.node_sat_degree=set_saturation_degree(v);
                v.node_color=access_sat[x].node_color;
                access_sat[x].node_sat_degree=v.node_sat_degree;
                vertex_dsat.add(v);
            }

        }
        /*Iterator<Vertex> irt=vertex_dsat.iterator();
        while (irt.hasNext())
        {
            Vertex v=irt.next();
            System.out.println(v.node_no+" "+v.node_degree+" "+v.node_color+" "+v.node_sat_degree);
        }*/
        boolean colors_available[]=new boolean[nodes+1];
        Arrays.fill(colors_available,true);

        while (found.size()!=nodes)
        {
            Vertex v2=vertex_dsat.poll();
            int v_no=v2.node_no;
            //access_sat[u_no].node_color=1;
            //System.out.println(v_no);
            if(!found.contains(v_no))
            {
                //System.out.println(found.size()+" "+v_no+" "+v2.node_sat_degree);
                found.add(v_no);
                //boolean colors_available[]=new boolean[nodes+1];
                //Arrays.fill(colors_available,true);

                Iterator<Integer> it2=adjList[v_no].iterator();
                while (it2.hasNext())
                {
                    int b=it2.next();
                    if(access_sat[b].node_color!=0)
                    {
                        colors_available[access_sat[b].node_color]=false;
                    }
                }
                int color;
                for(color=1;color<nodes+1;color++)
                {
                    if(colors_available[color])
                    {
                        break;
                    }
                }
                access_sat[v_no].node_color=color;

                Arrays.fill(colors_available,true);

                Iterator<Integer> it3=adjList[v_no].iterator();
                //Vertex v_p=new Vertex();
               // v_p.node_color=color;
               // v_p.node_no=v_no;
                while (it3.hasNext())
                {
                    int x=it3.next();
                    if(!found.contains(x))
                    {
                        Vertex v=new Vertex();
                        v.node_no=x;
                        v.node_degree=access_sat[x].node_degree;
                        //v.node_sat_degree=access_sat[x].node_sat_degree+1;
                        v.node_sat_degree=set_saturation_degree(v);
                        v.node_color=access_sat[x].node_color;
                        access_sat[x].node_sat_degree=v.node_sat_degree;
                        vertex_dsat.add(v);
                    }

                }
                /*Iterator<Vertex> itt=vertex_dsat.iterator();
                while (itt.hasNext())
                {
                    Vertex v=itt.next();
                    System.out.println(v.node_no+" "+v.node_degree+" "+v.node_color+" "+v.node_sat_degree);
                }*/
            }
        }
    }
    public void print_after_dsat()
    {
        for(int i=1;i<nodes+1;i++)
        {
            if(access_sat[i].node_color>max_timeslot_dsat)
            {
                max_timeslot_dsat=access_sat[i].node_color;
            }
            //System.out.println(i+" "+access_sat[i].node_color);
        }
        //System.out.println(max_timeslot_dsat);

    }
    public void penalty_after_dsat()
    {
        float total_penalty=0;

        for(int i=0;i<students;i++)
        {
            float individual_penalty=0;
            int len=each_course_of_students[i].size();
            Integer arr[]= new Integer[len];
            Iterator<Integer> it=each_course_of_students[i].iterator();
            int j=0;
            while (it.hasNext())
            {
                arr[j]=access_sat[it.next()].node_color;
                j++;
            }
            Arrays.sort(arr, Collections.reverseOrder());
            for(int k=0;k<len-1;k++)
            {
                //for(int p=k+1;p<len;p++) {

                    int x=arr[k]-arr[k+1]-1;
                    if(x>=5)
                    {
                        individual_penalty+=0;
                    }
                    else if(x==4)
                    {
                        individual_penalty+=1;
                    }
                    else if(x==3)
                    {
                        individual_penalty+=2;
                    }
                    else if(x==2)
                    {
                        individual_penalty+=4;
                    }
                    else if(x==1)
                    {
                        individual_penalty+=8;
                    }
                    else individual_penalty+=16;
                //}
            }
            total_penalty+=individual_penalty;
        }
        //System.out.println(total_penalty/students);
        penalty_dsatur=total_penalty/students;
        prev_penalty_dsatur=penalty_dsatur;
    }

    public void penalty_after_largest_degree()
    {
        float total_penalty=0;

        for(int i=0;i<students;i++)
        {
            float individual_penalty=0;
            int len=each_course_of_students[i].size();
            Integer arr[]= new Integer[len];
            Iterator<Integer> it=each_course_of_students[i].iterator();
            int j=0;
            while (it.hasNext())
            {
                arr[j]=largest_degree_result[it.next()];
                j++;
            }
            Arrays.sort(arr, Collections.reverseOrder());
            for(int k=0;k<len-1;k++)
            {
                //for(int p=k+1;p<len;p++) {

                    int x=arr[k]-arr[k+1]-1;
                    if(x>=5)
                    {
                        individual_penalty+=0;
                    }
                    else if(x==4)
                    {
                        individual_penalty+=1;
                    }
                    else if(x==3)
                    {
                        individual_penalty+=2;
                    }
                    else if(x==2)
                    {
                        individual_penalty+=4;
                    }
                    else if(x==1)
                    {
                        individual_penalty+=8;
                    }
                    else individual_penalty+=16;
                //}
            }
            total_penalty+=individual_penalty;
        }
        //System.out.println(total_penalty/students);
        penalty_largest_degree=total_penalty/students;
        prev_penalty_largest_degree=penalty_largest_degree;
    }

    public void copy_result()
    {
        largest_degree_copy=new int[nodes+1];
        dsatur_copy=new int[nodes+1];

        for(int i=1;i<nodes+1;i++)
        {
            largest_degree_copy[i]=largest_degree_result[i];
            dsatur_copy[i]=access_sat[i].node_color;
        }
    }

    public void bfs_for_kempe_chain_largest_degree(int node1, int color1, int color2)
    {
        boolean visited[] = new boolean[nodes+1];
        kempe_chain_elements=new HashSet<>();

        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[node1]=true;
        queue.add(node1);

        while (queue.size() != 0)
        {
            node1 = queue.poll();
            kempe_chain_elements.add(node1);

            Iterator<Integer> i = adjList[node1].iterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n] && (largest_degree_copy[n]==color1 || largest_degree_copy[n]==color2))
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
    public void penalty_kempe_largest_degree(int color1, int color2)
    {
        int copy_of_prev_color[]=new int[nodes+1];

        for(int i=1;i<nodes+1;i++)
        {
            copy_of_prev_color[i]=largest_degree_copy[i];
        }
        Iterator<Integer> it=kempe_chain_elements.iterator();
        while(it.hasNext()){
            int i=it.next();
            if(largest_degree_copy[i]==color1)
            {
                largest_degree_copy[i]=color2;
            }
            else
            {
                largest_degree_copy[i]=color1;
            }
        }
        float total_penalty=0;

        for(int i=0;i<students;i++)
        {
            float individual_penalty=0;
            int len=each_course_of_students[i].size();
            Integer arr[]= new Integer[len];
            Iterator<Integer> itr=each_course_of_students[i].iterator();
            int j=0;
            while (itr.hasNext())
            {
                arr[j]=largest_degree_copy[itr.next()];
                j++;
            }
            Arrays.sort(arr, Collections.reverseOrder());
            for(int k=0;k<len-1;k++)
            {
               // for(int p=k+1;p<len;p++) {

                    int x=arr[k]-arr[k+1]-1;
                    if(x>=5)
                    {
                        individual_penalty+=0;
                    }
                    else if(x==4)
                    {
                        individual_penalty+=1;
                    }
                    else if(x==3)
                    {
                        individual_penalty+=2;
                    }
                    else if(x==2)
                    {
                        individual_penalty+=4;
                    }
                    else if(x==1)
                    {
                        individual_penalty+=8;
                    }
                    else individual_penalty+=16;
                //}
            }
            total_penalty+=individual_penalty;
        }
        float new_penalty=total_penalty/students;
        //System.out.println(new_penalty);
        if(new_penalty<prev_penalty_largest_degree)
        {
            prev_penalty_largest_degree=new_penalty;
        }
        else
        {
            for(int i=1;i<nodes+1;i++)
            {
                largest_degree_copy[i]=copy_of_prev_color[i];
            }
        }
        /*int max_after_kempe=-1;
        for(int i=1;i<nodes+1;i++)
        {
            if(largest_degree_copy[i]>max_after_kempe)
            {
                max_after_kempe=largest_degree_copy[i];
            }
            System.out.println(i+" "+largest_degree_copy[i]);
        }
        System.out.println(max_after_kempe);*/
    }

    public void Kempe_Chain_after_largest_degree()
    {
        copy_result();
        Random rand=new Random();
        try
        {
            for(int i=0;i<5000;i++)
            {
                int rand1=0;
                rand1=rand.nextInt(nodes+1);
                while (rand1==0 || adjList[rand1].size()==0)
                {
                    rand1=rand.nextInt(nodes+1);
                }
                int rand2=0;
                rand2=rand.nextInt(adjList[rand1].size());

                Iterator<Integer> it=adjList[rand1].iterator();
                int f=0;
                while (it.hasNext())
                {
                    if(f==rand2)
                    {
                        rand2=it.next();
                        break;
                    }
                    f++;
                }
                bfs_for_kempe_chain_largest_degree(rand1,largest_degree_copy[rand1],largest_degree_copy[rand2]);
                penalty_kempe_largest_degree(largest_degree_copy[rand1],largest_degree_copy[rand2]);
            }

        }catch(ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        //System.out.println(prev_penalty_largest_degree);
        int max_after_kempe=-1;
        for(int i=1;i<nodes+1;i++)
        {
            if(largest_degree_copy[i]>max_after_kempe)
            {
                max_after_kempe=largest_degree_copy[i];
            }
            //System.out.println(i+" "+largest_degree_copy[i]);
        }
        //System.out.println(max_after_kempe);
    }

    public void bfs_for_kempe_chain_DSatur(int node1, int color1, int color2)
    {
        boolean visited[] = new boolean[nodes+1];
        kempe_chain_elements=new HashSet<>();

        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[node1]=true;
        queue.add(node1);

        while (queue.size() != 0)
        {
            node1 = queue.poll();
            kempe_chain_elements.add(node1);

            Iterator<Integer> i = adjList[node1].iterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n] && (dsatur_copy[n]==color1 || dsatur_copy[n]==color2))
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
    public void penalty_kempe_DSatur(int color1, int color2)
    {
        int copy_of_prev_color[]=new int[nodes+1];

        for(int i=1;i<nodes+1;i++)
        {
            copy_of_prev_color[i]=dsatur_copy[i];
        }
        Iterator<Integer> it=kempe_chain_elements.iterator();
        while(it.hasNext()){
            int i=it.next();
            if(dsatur_copy[i]==color1)
            {
                dsatur_copy[i]=color2;
            }
            else
            {
                dsatur_copy[i]=color1;
            }
        }
        float total_penalty=0;


        for(int i=0;i<students;i++)
        {
            float individual_penalty=0;
            int len=each_course_of_students[i].size();
            Integer arr[]= new Integer[len];
            Iterator<Integer> itr=each_course_of_students[i].iterator();
            int j=0;
            while (itr.hasNext())
            {
                arr[j]=dsatur_copy[itr.next()];
                j++;
            }
            Arrays.sort(arr, Collections.reverseOrder());
            for(int k=0;k<len-1;k++)
            {
               // for(int p=k+1;p<len;p++) {

                    int x=arr[k]-arr[k+1]-1;
                    if(x>=5)
                    {
                        individual_penalty+=0;
                    }
                    else if(x==4)
                    {
                        individual_penalty+=1;
                    }
                    else if(x==3)
                    {
                        individual_penalty+=2;
                    }
                    else if(x==2)
                    {
                        individual_penalty+=4;
                    }
                    else if(x==1)
                    {
                        individual_penalty+=8;
                    }
                    else individual_penalty+=16;
               // }
            }
            total_penalty+=individual_penalty;
        }
        float new_penalty=total_penalty/students;
        //System.out.println(new_penalty);
        if(new_penalty<prev_penalty_dsatur)
        {
            prev_penalty_dsatur=new_penalty;
        }
        else
        {
            for(int i=1;i<nodes+1;i++)
            {
                dsatur_copy[i]=copy_of_prev_color[i];
            }
        }
        /*int max_after_kempe=-1;
        for(int i=1;i<nodes+1;i++)
        {
            if(largest_degree_copy[i]>max_after_kempe)
            {
                max_after_kempe=largest_degree_copy[i];
            }
            System.out.println(i+" "+largest_degree_copy[i]);
        }
        System.out.println(max_after_kempe);*/
    }

    public void Kempe_Chain_after_DSatur()
    {
        copy_result();
        Random rand=new Random();
        try
        {
            for(int i=0;i<500;i++)
            {
                int rand1=0;
                rand1=rand.nextInt(nodes+1);
                while (rand1==0 || adjList[rand1].size()==0)
                {
                    rand1=rand.nextInt(nodes+1);
                }
                int rand2=0;
                rand2=rand.nextInt(adjList[rand1].size());

                Iterator<Integer> it=adjList[rand1].iterator();
                int f=0;
                while (it.hasNext())
                {
                    if(f==rand2)
                    {
                        rand2=it.next();
                        break;
                    }
                    f++;
                }
                bfs_for_kempe_chain_DSatur(rand1,dsatur_copy[rand1],dsatur_copy[rand2]);
                penalty_kempe_DSatur(dsatur_copy[rand1],dsatur_copy[rand2]);
            }

        }catch(ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        //System.out.println(prev_penalty_dsatur);
        int max_after_kempe=-1;
        for(int i=1;i<nodes+1;i++)
        {
            if(dsatur_copy[i]>max_after_kempe)
            {
                max_after_kempe=dsatur_copy[i];
            }
            //System.out.println(i+" "+largest_degree_copy[i]);
        }
        //System.out.println(max_after_kempe);
    }



    public int getMax_timeslot_largest_degree()
    {
        return max_timeslot_largest_degree;
    }

    public int getMax_timeslot_dsat() {
        return max_timeslot_dsat;
    }

    public float getPenalty_largest_degree() {
        return penalty_largest_degree;
    }

    public float getPenalty_dsatur() {
        return penalty_dsatur;
    }

    public float getPrev_penalty_largest_degree() {
        return prev_penalty_largest_degree;
    }

    public float getPrev_penalty_dsatur() {
        return prev_penalty_dsatur;
    }


    public void print_sol_file(String s)
    {
        try{
            PrintWriter pr=new PrintWriter(new FileWriter(s+".sol"));
            pr.write("Courses  Timeslots\n");
            for(int i=1;i<nodes+1;i++)
            {
                pr.write(i+"        "+largest_degree_result[i]+"\n");
            }
            pr.flush();
            pr.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
