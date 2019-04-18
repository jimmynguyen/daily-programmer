/*
Solution to /r/dailyprogrammer Challenge #9 Intermediate
Jimmy Nguyen

Link to challenge:
    https://www.reddit.com/r/dailyprogrammer/comments/pu1y6/2172012_challenge_9_intermediate/

Usage:
    go run c009i.go
*/
package main

import (
	"bufio"
	"fmt"
	"io/ioutil"
	"os"
	"strings"
)

func checkError(e error) {
	if e != nil {
		panic(e)
	}
}

func askUser(question string, reader *bufio.Reader) string {
	fmt.Print(question)
	answer, err := reader.ReadString('\n')
	checkError(err)
	return strings.TrimSuffix(answer, "\n")
}

func readFile(filePath string) string {
	fileBuffer, err := ioutil.ReadFile(filePath)
	checkError(err)
	return string(fileBuffer)
}

func writefile(filePath string, file string) {
	err := ioutil.WriteFile(filePath, []byte(file), 0644)
	checkError(err)
}

func main() {
	reader := bufio.NewReader(os.Stdin)
	filePath := askUser("Enter file path: ", reader)
	toSearchFor := askUser("Enter string to search for: ", reader)
	toReplaceWith := askUser("Enter string to replace with: ", reader)
	file := strings.ReplaceAll(readFile(filePath), toSearchFor, toReplaceWith)
	writefile(filePath, file)
	fmt.Printf("Successfully replaced \"%s\" with \"%s\" in %s\n", toSearchFor, toReplaceWith, filePath)
}
