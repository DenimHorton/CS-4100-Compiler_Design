Copy and paste below code into a .sh file and run the program with the










echo "Compiling test05.java . . ."
javac ./SP22HW5/*.java -d ../Compiler-Part05
javac ./ADT/*.java

echo "Running with input ./SP22HW5/Inputs/MinimumSyntax-1-ASP22.txt outputting to file ./SP22HW5/Outputs/MinimumSyntax-1-OUTPUT.txt"
java SP22HW5/test05.java "./SP22HW5/Inputs/MinimumSyntax-1-ASP22.txt" >> ./SP22HW5/Outputs/MinimumSyntax-1-OUTPUT.txt

echo "Running with input ./SP22HW5/Inputs/BadSyntax-1-ASP22.txt outputting to file ./SP22HW5/Outputs/BadSyntax-1-OUTPUT.txt"
java SP22HW5/test05.java "./SP22HW5/Inputs/BadSyntax-1-ASP22.txt"  >> ./SP22HW5/Outputs/BadSyntax-1-OUTPUT.txt

echo "Running with input ./SP22HW5/Inputs/BadSyntax-2-ASP22.txt outputting to file ./SP22HW5/Outputs/BadSyntax-2-OUTPUT.txt"
java SP22HW5/test05.java "./SP22HW5/Inputs/BadSyntax-2-ASP22.txt"  >> ./SP22HW5/Outputs/BadSyntax-2-OUTPUT.txt

echo "Running with input ./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt outputting to file ./SP22HW5/Outputs/GoodSyntax-1-OUTPUT.txt"
java SP22HW5/test05.java "./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt" >> ./SP22HW5/Outputs/GoodSyntax-1-OUTPUT.txt

echo "diff'-ing output with expected output for test MinimumSyntax-1-ASP22"
java SP22HW5/test05.java "./SP22HW5/Inputs/MinimumSyntax-1-ASP22.txt" > ./SP22HW5/Outputs/output.txt
diff --color -b -c -s ./SP22HW5/Expected/MinimumSyntax-1-EXPECTED.txt ./SP22HW5/Outputs/output.txt  > ./SP22HW5/Outputs/MinimumSyntax-1-COMPARE.txt

echo "diff'-ing output with expected output for test BadSyntax-1-ASP22"
java SP22HW5/test05.java "./SP22HW5/Inputs/BadSyntax-1-ASP22.txt" > ./SP22HW5/Outputs/output.txt
diff --color -b -c -s ./SP22HW5/Expected/BadSyntax-1-EXPECTED.txt ./SP22HW5/Outputs/output.txt  > ./SP22HW5/Outputs/BadSyntax-1-COMPARE.txt

echo "diff'-ing output with expected output for test BadSyntax-2-ASP22"
java SP22HW5/test05.java "./SP22HW5/Inputs/BadSyntax-2-ASP22.txt" > ./SP22HW5/Outputs/output.txt
diff --color -b -c -s ./SP22HW5/Expected/BadSyntax-2-EXPECTED.txt ./SP22HW5/Outputs/output.txt  > ./SP22HW5/Outputs/BadSyntax-2-COMPARE.txt

echo "diff'-ing output with expected output for test GoodSyntax-1-ASP22"
java SP22HW5/test05.java "./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt" > ./SP22HW5/Outputs/output.txt
diff --color -b -c -s ./SP22HW5/Expected/GoodSyntax-1-EXPECTED.txt ./SP22HW5/Outputs/output.txt  > ./SP22HW5/Outputs/GoodSyntax-1-COMPARE.txt

cd ./SP22HW5
find . -name "*.class" -type f -delete

cd ../ADT
find . -name "*.class" -type f -delete
