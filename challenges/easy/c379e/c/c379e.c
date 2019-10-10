/*
 * Solution to /r/dailyprogrammer Challenge #379 Easy
 * Jimmy Nguyen
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/cdieag/20190715_challenge_379_easy_progressive_taxation/
 *
 * Usage (MacOS):
 *     clang c379e.c && ./a.out [tax_table_file] [income]
 *         - returns tax amount owed for given [income] based on tax bracket in [filename]
 */
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct tax_bracket {
	int income_cap;
	double marginal_tax_rate;
};

struct tax_table {
	int size;
	struct tax_bracket** brackets;
};

void print_tax_table(struct tax_table* table) {
	printf("tax_table {\n  size: %d,\n  brackets:\n", table->size);
	for (int i = 0; i < table->size; i++) {
		printf("  {\n    income_cap: %d,\n    marginal_tax_rate: %0.2f\n  }\n", table->brackets[i]->income_cap, table->brackets[i]->marginal_tax_rate);
	}
	printf("}\n");
}

void print_tax_bracket(struct tax_bracket* bracket) {
	printf("tax_bracket {\n  income_cap: %d,\n  marginal_tax_rate: %0.2f\n}\n", bracket->income_cap, bracket->marginal_tax_rate);
}

struct tax_bracket* parse_tax_bracket(char* line) {
	struct tax_bracket* bracket = (struct tax_bracket*)malloc(sizeof(struct tax_bracket));
	char str[1024];
	int ndx = 0;
	while (*line != '\0') {
		if (*line == ',') {
			str[ndx++] = '\0';
			bracket->income_cap = *str == '-' ? -1 : strtol(&str[0], NULL, 10);
			ndx = 0;
		} else {
			str[ndx++] = *line;
		}
		line++;
	}
	str[ndx++] = '\0';
	bracket->marginal_tax_rate = strtod(&str[0], NULL);
	return bracket;
}

void strip_newline(char* line) {
	size_t len = strlen(line);
	line[--len] = '\0';
}

struct tax_table* get_tax_table(char* filename) {
	FILE* fh = fopen(filename, "r");
	if (fh == NULL) {
		perror("Unable to open file!");
		exit(1);
	}

	struct tax_table* table = (struct tax_table*)malloc(sizeof(struct tax_table));
	table->size = 0;
	table->brackets = NULL;

	char line[1024];
	// ignore header line
	fgets(line, 1024, fh);
	while (fgets(line, 1024, fh)) {
		table->size++;
		strip_newline(line);
		if (NULL == table->brackets) {
			table->brackets = (struct tax_bracket**)malloc(sizeof(struct tax_bracket*)*table->size);
		} else {
			table->brackets = (struct tax_bracket**)realloc(table->brackets, sizeof(struct tax_bracket*)*table->size);
		}
		table->brackets[table->size-1] = parse_tax_bracket(line);
	}
	fclose(fh);
	return table;
}

int tax(int income, struct tax_table* table) {
	int prev_income_cap = 0;
	double total_tax = 0;
	for (int i = 0; i < table->size; i++) {
		struct tax_bracket* bracket = table->brackets[i];
		if (income < bracket->income_cap || bracket->income_cap == -1) {
			total_tax += (income - prev_income_cap) * bracket->marginal_tax_rate;
			break;
		} else {
			total_tax += (bracket->income_cap - prev_income_cap) * bracket->marginal_tax_rate;
			prev_income_cap = bracket->income_cap;
		}
	}
	return (int)total_tax;
}

void free_table(struct tax_table* table) {
	for (int i = 0; i < table->size; i++) {
		free(table->brackets[i]);
	}
	free(table->brackets);
	free(table);
}

int main(int argc, char *argv[]) {
	if (argc < 2 || argc > 3) {
		printf("invalid function call\n");
	} else if (argc == 2 && (strcmp("-t", argv[1]) == 0 || strcmp("--test", argv[1]) == 0)) {
		// ./c379e [-t or --test]
		int tests[7][2] = {
			{0, 0},
			{10000, 0},
			{10009, 0},
			{10010, 1},
			{12000, 200},
			{56789, 8697},
			{1234567, 473326}
		};
		struct tax_table* table = get_tax_table("../tax_table.csv");
		for (int i = 0; i < 7; i++) {
			int input = tests[i][0];
			int expected_output = tests[i][1];
			int actual_output = tax(input, table);
			bool fail = actual_output != expected_output;
			printf("test #%d:\n", i + 1);
			if (fail) {
				printf("  FAILED: expected tax(%d) = %d but found %d\n", input, expected_output, actual_output);
			} else {
				printf("  PASSED: tax(%d) = %d\n", input, actual_output);
			}
		}
		free_table(table);
	} else {
		// ./c379e [tax_table_file] [income]
		struct tax_table* table = get_tax_table(argv[1]);
		printf("tax(%s) => %d\n", argv[2], tax(strtol(argv[2], NULL, 10), table));
		free_table(table);
	}
	return 0;
}
