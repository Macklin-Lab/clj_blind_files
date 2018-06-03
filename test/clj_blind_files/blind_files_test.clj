(ns clj-blind-files.blind_files-test
  (:require [clojure.test :refer :all]
            [clj-blind-files.blind_files :refer :all]))


;; test values for development
(def test-dir "/Users/Nick/personal_projects/clj_blind_files/test/test-img")
(def test-file "/Users/Nick/personal_projects/clj_blind_files/test/test-img/2018-02-25_adult-wt_adultSham3_img041_data_tuned_roi1_decent-img.tif")
(def file-end ".tif")
(def test-bad "/Users/Nick/personal_projects/clj_blind_files/test/test-img/.hidden-test.tif")


(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
