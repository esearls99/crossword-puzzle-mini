Enter something of the form into the terminal after compilation:
java Crossword <size> <word list> <graphics mode> <generation mode>

Size can be [3 | 4]. The brute force method can do size 4. DFS is **not** recommended for size 4.

The 3 different word banks are [standard | names | acronyms]. Standard runs very slowly on DFS. I think it is because DFS runs slower with more words.

Choose between [console | graphics]. console writes into the terminal while graphics creates a window and displays the crossword.

Choose the generation method [brutal | DFS]. Choose brutal for brute force and DFS for depth first search.
