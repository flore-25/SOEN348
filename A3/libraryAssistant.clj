(ns library-system.core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; EDN File Operations
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn load-library
  [filename]
  ;; TODO: Read EDN file and return vector of books
  ;; Hint: use slurp + clojure.edn/read-string
  [])

(defn save-library
  [filename books]
  ;; TODO: Write books back to EDN file
  ;; Hint: use spit + pr-str
  nil)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Display Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn display-book
  [book]
  ;; TODO: Print a single book in formatted style
  nil)

(defn display-books
  [books]
  ;; TODO:
  ;; - print total count
  ;; - sort by year
  ;; - display each book using display-book
  nil)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Search Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn search-by-title
  [books]
  ;; TODO:
  ;; - prompt user for input
  ;; - case-insensitive search in :title
  ;; - display results
  nil)

(defn search-by-author
  [books]
  ;; TODO:
  ;; - prompt user for input
  ;; - case-insensitive search in :author
  ;; - display results
  nil)

(defn search-menu
  [books]
  (println)
  (println "1. Search by Title")
  (println "2. Search by Author")
  (print "Enter option: ")
  (flush)

  (let [choice (read-line)]
    (case choice
      "1" (search-by-title books)
      "2" (search-by-author books)
      (println "Invalid option"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Filter Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn filter-by-genre
  [books]
  ;; TODO:
  ;; - prompt user
  ;; - filter by :genre (case-sensitive)
  ;; - display results
  nil)

(defn filter-by-year-range
  [books]
  ;; TODO:
  ;; - prompt user for min and max year
  ;; - filter books in range (inclusive)
  ;; - display results
  nil)

(defn filter-menu
  [books]
  ;; TODO:
  ;; - sub-menu for filter options
  ;; - call correct function
  nil)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Add Book
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-book
  [books]
  ;; TODO:
  ;; - prompt user for all fields
  ;; - create book map
  ;; - return (conj books new-book)
  books)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Statistics
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn library-statistics
  [books]
  ;; TODO:
  ;; - total books
  ;; - oldest book (min-key :year)
  ;; - newest book (max-key :year)
  ;; - unique genres (set)
  ;; - books per genre (group-by)
  nil)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Main Menu
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn print-main-menu
  []
  (println)
  (println "=======================================")
  (println " Functional Library Information System")
  (println "=======================================")
  (println)
  (println "1. View Library Collection")
  (println "2. Search Library")
  (println "3. Filter Books")
  (println "4. Add a New Book")
  (println "5. Library Statistics")
  (println "6. Save and Exit")
  (println)
  (print "Enter option: ")
  (flush))

(defn menu-loop
  [books filename]

  (print-main-menu)

  (let [choice (read-line)]

    (case choice

      "1"
      (do
        (display-books books)
        (recur books filename))

      "2"
      (do
        (search-menu books)
        (recur books filename))

      "3"
      (do
        (filter-menu books)
        (recur books filename))

      "4"
      (recur (add-book books) filename)

      "5"
      (do
        (library-statistics books)
        (recur books filename))

      "6"
      (do
        (save-library filename books)
        (println)
        (println "Library collection saved successfully.")
        (println "Exiting program..."))

      (do
        (println "Invalid option.")
        (recur books filename)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Program Entry Point
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn -main
  []
  (menu-loop
    (load-library "library.edn")
    "library.edn"))

(-main)