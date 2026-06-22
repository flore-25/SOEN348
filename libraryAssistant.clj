(ns library-system.core
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.pprint :as pprint]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; EDN File Operations
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn load-library
  [filename]
  ;; Read EDN file and return vector of books.
  ;; Hint: use slurp + clojure.edn/read-string
  (edn/read-string (slurp filename)))

(defn save-library
  [filename books]
  ;; Write books back to EDN file.
  ;; Hint: use spit + pr-str
  (spit filename (with-out-str (pprint/pprint books))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Display Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn display-book
  [book]
  ;; Print a single book in formatted style.
  (println "----------------------------------")
  (println "ID:" (:id book))
  (println "Title:" (:title book))
  (println "Author:" (:author book))
  (println "Genre:" (:genre book))
  (println "Year:" (:year book)))

(defn display-books
  [books]
  ;; - print total count
  ;; - sort by year
  ;; - display each book using display-book
  (if (empty? books)
    (println "No books found.")
    (do
      (println)
      (println "Total Books:" (count books))
      (doseq [book (sort-by :year books)]
        (display-book book)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Search Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn contains-ignore-case?
  [text fragment]
  (str/includes? (str/lower-case text) (str/lower-case fragment)))

(defn search-by-title
  [books]
  ;; - prompt user for input
  ;; - case-insensitive search in :title
  ;; - display results
  (println)
  (print "Enter title fragment: ")
  (flush)
  (let [fragment (read-line)
        matches (filter #(contains-ignore-case? (:title %) fragment) books)]
    (display-books matches)))

(defn search-by-author
  [books]
  ;; - prompt user for input
  ;; - case-insensitive search in :author
  ;; - display results
  (println)
  (print "Enter author name: ")
  (flush)
  (let [fragment (read-line)
        matches (filter #(contains-ignore-case? (:author %) fragment) books)]
    (display-books matches)))

(defn search-menu
  [books]
  (println)
  (println "1. Search by Title")
  (println "2. Search by Author")
  (println)
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
  ;; - prompt user
  ;; - filter by :genre (case-sensitive)
  ;; - display results
  (println)
  (print "Enter genre: ")
  (flush)
  (let [genre (read-line)
        matches (filter #(= (:genre %) genre) books)]
    (display-books matches)))

(defn filter-by-year-range
  [books]
  ;; - prompt user for min and max year
  ;; - filter books in range (inclusive)
  ;; - display results
  (println)
  (print "Enter minimum year: ")
  (flush)
  (let [minimum-year (Integer/parseInt (read-line))]
    (print "Enter maximum year: ")
    (flush)
    (let [maximum-year (Integer/parseInt (read-line))
          matches (filter #(<= minimum-year (:year %) maximum-year) books)]
      (display-books matches))))

(defn filter-menu
  [books]
  ;; - sub-menu for filter options
  ;; - call correct function
  (println)
  (println "1. Filter by Genre")
  (println "2. Filter by Publication Year Range")
  (println)
  (print "Enter option: ")
  (flush)
  (let [choice (read-line)]
    (case choice
      "1" (filter-by-genre books)
      "2" (filter-by-year-range books)
      (println "Invalid option"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Add Book
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-book
  [books]
  ;; - prompt user for all fields
  ;; - create book map
  ;; - return (conj books new-book)
  (println)
  (print "Enter Book ID: ")
  (flush)
  (let [id (read-line)]
    (print "Enter Title: ")
    (flush)
    (let [title (read-line)]
      (print "Enter Author: ")
      (flush)
      (let [author (read-line)]
        (print "Enter Genre: ")
        (flush)
        (let [genre (read-line)]
          (print "Enter Publication Year: ")
          (flush)
          (let [year (Integer/parseInt (read-line))
                new-book {:id id
                          :title title
                          :author author
                          :genre genre
                          :year year}]
            (println)
            (println "Book added successfully.")
            (conj books new-book)))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Statistics
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn library-statistics
  [books]
  ;; - total books
  ;; - oldest book (min-key :year)
  ;; - newest book (max-key :year)
  ;; - unique genres (set)
  ;; - books per genre (group-by)
  (println)
  (println "Total Number of Books:" (count books))
  (if (empty? books)
    (println "No books found.")
    (let [oldest-book (apply min-key :year books)
          newest-book (apply max-key :year books)
          grouped-by-genre (group-by :genre books)
          unique-genres (reduce conj #{} (keys grouped-by-genre))
          books-per-genre (frequencies (map :genre books))]
      (println)
      (println "Oldest Book:")
      (display-book oldest-book)
      (println)
      (println "Newest Book:")
      (display-book newest-book)
      (println)
      (println "Number of Unique Genres:" (count unique-genres))
      (println)
      (println "Books per Genre:")
      (doseq [[genre total] (sort books-per-genre)]
        (println (str genre ": " total))))))

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
