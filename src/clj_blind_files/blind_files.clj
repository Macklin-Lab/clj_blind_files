;; nice project template http://howistart.org/posts/clojure/1/
(ns clj-blind-files.blind_files)

(require '[clojure.java.io :as io])
(require '[clojure.data.csv :as csv])


;; I need a few functions that are composable to finish the raw part of the project. The GUI will be separate
;; Functions needed:
;; 1. DONE +list file names of a given ending+
;;  a. DONE +exclude those staring with "."+
;; 2. DONE +get just file names and split the full path.+
;; 3. DONE +replace the file name with a uuid random name+
;; 4. DONE +make a new folder called "Blinded" in the initial folder.+
;; 5. DONE +make a map of old-names:new-names+
;; 5. TODO -write old-name, new-name to a KEY.csv in the original folder-
;; 6. TODO -write all new-names to a blind-ref.csv in the new folder.-
;; 7. TODO -GUI-


;; make uuids for blinded names
(defn uuid [] (str (java.util.UUID/randomUUID)))

;; main entry point. Make blinded folder if it doesn't exist. 
(defn make-blinded-folder
  "main entry point. Check if blinded exists. If yes, quit/throw exception
   else, proceed and return the blinded path after making the folder." 
  [path] 
  (let [bp (io/file path "blinded")] 
    (if (.exists bp) 
      (throw (Exception. "Blinded files already exist. Quitting"))
      (.mkdir bp))(str bp)))

;; filtered list of files based on file ending selection
(defn list-files
    "List files only of a specific ending given a directory. 
  filters out files starting with ."
  [ending dir]
  (remove #(.startsWith (str (.getName (io/file %))) ".")
   (filter #(.endsWith (str %) ending)
            (.listFiles (io/file dir)))))

;; rather than blinded-map, make blinded-list. A list of lists with original name and blinded names
(defn blinded-list 
 "make a list of lists containingfiles and uuid blinded names."
  [files]
  (into () 
   (for [f files] 
    (list f
     (str (io/file (str (.getParent f)) "blinded" (str (uuid) ".tif")))))))

;; get file names
(defn name
  "return file name helper function"
  [f]
  (.getName (io/file f)))


(defn write-csv 
  [header file csv] 
  (with-open [out-file (clojure.java.io/writer file)] 
             (csv/write-csv out-file (conj csv header))))


(defn parent
  "return the path"
  [f]
  (.getParent (io/file f)))

  
;; testing/play below

;; test values for development
(def test-dir "/Users/Nick/personal_projects/clj_blind_files/test/test-img")
(def test-file "/Users/Nick/personal_projects/clj_blind_files/test/test-img/2018-02-25_adult-wt_adultSham3_img041_data_tuned_roi1_decent-img.tif")
(def file-end ".tif")
(def test-bad "/Users/Nick/personal_projects/clj_blind_files/test/test-img/.hidden-test.tif")
(def blind "blinded")


(def blind-t
 (list-files file-end test-dir))

; make blind map
(def blindlist
  (blinded-list blind-t))
blindlist



(with-open [out-file (  
                      clojure.java.io/writer "/Users/Nick/personal_projects/clj_blind_files/test/test-img/blinded/KEY.csv")] 
  (csv/write-csv out-file (conj blindlist ["original" "blinded"])))
;; other functions I may need. 


;; combine parent and child. 
(defn pathsplitter
  "returns a list with the path and filename"
  [f] (let [fileio (io/file f)]
        (list (.getParent fileio)
              (.getName fileio))))

;; not needed not
;; associate the keys (original files) and values (uuid replaced files) 
(defn blinded-map
  "returns a map of regular full file paths as keys 
  and full blinded file paths as their values."
  [files]
 (into {}
  (for [f files] 
   (assoc {} (str f) 
    (str (io/file (str (.getParent f)) "blinded" (str (uuid) ".tif")))))))


(defn list-map-names
  "convert the map to a list"
   [filelist]
  (for [f filelist] (map name f)))
