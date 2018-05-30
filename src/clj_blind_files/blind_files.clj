;; nice project template http://howistart.org/posts/clojure/1/
(ns clj-blind-files.blind_files)

(require '[clojure.java.io :as io])

;; test values for development
(def test-dir "/Users/Nick/personal_projects/clj_blind_files/test/test-img")
(def test-file "/Users/Nick/personal_projects/clj_blind_files/test/test-img/2018-02-25_adult-wt_adultSham3_img041_data_tuned_roi1_decent-img.tif")
(def file-end ".tif")


;; I need a few functions that are composable to finish the raw part of the project. The GUI will be separate
;; Functions needed:
;; 1. DONE +list file names of a given ending+
;; 2. TODO -get just file names from the full path.-
;; 3. TODO -replace the file name with a uuid random name-
;; 4. TODO -make a new folder called "Blinded" in the initial folder.-
;; 5. TODO -write old-name, new-name to a KEY.csv in the original folder-
;; 6. TODO -write all new-names to a blind-ref.csv in the new folder.-


(defn list-files
  "List files only of a specific ending given a directory"
  ;; fn #1
  [ending dir]
  (filter #(.endsWith (str %) ending) (mapv str (filter #(.isFile %) (file-seq (io/file dir))))))



(map .getName (map io/file (list-files file-end test-dir))) ;; need to do this but failing
