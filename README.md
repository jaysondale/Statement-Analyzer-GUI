## Statement Analyzer GUI
#### Author: Jayson Dale
# Goal
Statement Analyzer GUI is a tool that can be used to classify transactions from various sources. The user creates categories in which transactions can be inserted, resulting in a very clean transaction summary.

# How it Works
The statement analyzer software acceps CSV files which can be downloaded from an online banking system (NOTE: the system has only been tested using TD statements). The user can download credit card, checking account, or savings account statements into a folder on their computer. The user then specifies this location in the Statement Analyzer system which allows it to import all transactions. The user can also specify which month to pull transactions from.

Statement Analyzer is capable of...
- Import and classify transactions from all types of bank statements (credit cards, checking, and savings accounts)
- Using database of transaction identification maps to auto-classify all vendors that were previously classified
- Specifying which month to pull transactions from

For users who like keeping accurate records of their spending, this tool saves hours of tedious work sifting through bank statements.

# Next Steps
The following features are going to be added shortly:
- Allow the user to select multiple months at a time
- Handle doubly-mapped identifiers (collision avoidance or chaining to be determined)
- Allow user to specify substring maps (ie. if a group of identifiers all have a common substring, they can all be mapped to the same category with the click of a button)
- Indicate which statement the transaction was pulled from
- Extensive error handling improvements (detecting poorly formatted statements, repeated transactions, invalid file locations etc.)
- Create more category management tools (add/remove categories, view list of transactions within category, merge categories, etc.)
