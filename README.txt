INTRUCTIONS FOR USE:
Enter something of the form into the terminal after compilation:
java Crossword <size> <word list> <graphics mode> <generation mode>

Size can be [3 | 4]. The brute force method can do size 4. DFS is **not** recommended for size 4.

The 3 different word banks are [standard | names | acronyms]. Standard runs very slowly on DFS. I think it is because DFS runs slower with more words.

Choose between [console | graphics]. console writes into the terminal while graphics creates a window and displays the crossword.

Choose the generation method [brutal | DFS]. Choose brutal for brute force and DFS for depth first search.

INFO:

This is a project I worked on last year. 
At the time I was inspired by the New York Times daily miniature crossword app where each day there's a new "dense" miniature crossword. 
They tend to be fairly easy so I figured it might be fun to try to generate them randomly from a wordbank. 
I tried two approaches to this problem, using a simple brute force algorithm and a depth-first-search algorithm. 
Both are far too slow and can only compute up to a 4x4 crossword, although theoretically, this program could create a 5x5 crossword given enough time. 
I don't think I'm quite done with this project, since I was quite dissatisfied with its speed. 
In the future, I hope to try to implement another faster algorithm.
