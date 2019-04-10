/*
Solution to /r/dailyprogrammer Challenge #9 Hard
Jimmy Nguyen

Link to challenge:
    https://www.reddit.com/r/dailyprogrammer/comments/pu2c0/2172012_challenge_9_difficult/

Usage:
    go run c009h.go
*/
package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func checkError(e error) {
	if e != nil {
		panic(e)
	}
}

func buildPyramid(height int) []string {
	pyramid := make([]string, height)
	pyramid[0] = "1"
	for i := 1; i < height; i++ {
		prevLine := pyramid[i-1]
		currLine := ""
		j := 0
		k := 1
		L := len(prevLine)
		for j < L {
			if k == L || prevLine[j] != prevLine[k] {
				currLine = fmt.Sprintf("%s%d%s", currLine, k-j, string(prevLine[j]))
				j = k
			} else {
				k++
			}
		}
		pyramid[i] = currLine
	}
	return pyramid
}

func main() {
	reader := bufio.NewReader(os.Stdin)
	fmt.Print("Enter pyramid height: ")
	answer, err := reader.ReadString('\n')
	checkError(err)
	height, err := strconv.Atoi(strings.TrimSuffix(answer, "\n"))
	checkError(err)
	pyramid := buildPyramid(height)
	for i := 0; i < height; i++ {
		fmt.Println(pyramid[i])
	}
}
