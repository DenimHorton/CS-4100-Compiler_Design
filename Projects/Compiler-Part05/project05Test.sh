echo "Compiling test05.java . . ."
javac ./SP22HW5/*.java -d ../Compiler-Part05
javac ./ADT/*.java
echo "Executing compiled files . . . "
echo "-----------------------------------------------" > ./SP22HW5/Outputs/test_output.txt
echo "                Minimum - 1 - " >> ./SP22HW5/Outputs/test_output.txt
echo "                Test Output " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
java SP22HW5/test05.java "./SP22HW5/Inputs/MinimumSyntax-1-ASP22.txt" >> ./SP22HW5/Outputs/test_output.txt
echo " " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
echo "                Bad - 1 - " >> ./SP22HW5/Outputs/test_output.txt
echo "                Test Output " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
java SP22HW5/test05.java "./SP22HW5/Inputs/BadSyntax-1-ASP22.txt"  >> ./SP22HW5/Outputs/test_output.txt
echo " " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
echo "                Bad - 2 - " >> ./SP22HW5/Outputs/test_output.txt
echo "                Test Output " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
java SP22HW5/test05.java "./SP22HW5/Inputs/BadSyntax-2-ASP22.txt"  >> ./SP22HW5/Outputs/test_output.txt
echo " " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
echo "                Good - 1 - " >> ./SP22HW5/Outputs/test_output.txt
echo "                Test Output " >> ./SP22HW5/Outputs/test_output.txt
echo "-----------------------------------------------" >> ./SP22HW5/Outputs/test_output.txt
java SP22HW5/test05.java "./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt" >> ./SP22HW5/Outputs/test_output.txt
echo "Running main05 file and saving to ./SP22HW5/Outputs/main_output.txt . . ."
java SP22HW5/main05.java "./SP22HW5/Inputs/GoodSyntax-1-ASP22.txt" > ./SP22HW5/Outputs/main_output.txt
echo "diff'-ing output with expected output . . ."
diff --color -b -c -s ./SP22HW5/Outputs/GoodSyntax-1-EXOUTPUT.txt ./SP22HW5/Outputs/main_output.txt  > ./SP22HW5/Outputs/compare.txt
cd ./SP22HW5
find . -name "*.class" -type f -delete
cd ../ADT
find . -name "*.class" -type f -delete
