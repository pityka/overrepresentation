package object overrepresentation {

  def enrichmentTest(total: Int,
                     marked: Int,
                     draws: Int,
                     markedDraws: Int): Double =
    jdistlib.HyperGeometric.cumulative(
      markedDraws.toDouble - 1,
      marked.toDouble,
      total - marked.toDouble,
      draws.toDouble,
      false,
      false
    )

  def depletionTest(total: Int,
                    marked: Int,
                    draws: Int,
                    markedDraws: Int): Double =
    jdistlib.HyperGeometric.cumulative(
      markedDraws.toDouble,
      marked.toDouble,
      total - marked.toDouble,
      draws.toDouble,
      true,
      false
    )

  def enrichmentTest(c: Counts): Double =
    enrichmentTest(total = c.total,
                   marked = c.marked,
                   draws = c.draws,
                   markedDraws = c.markedDraws)

  def depletionTest(c: Counts): Double =
    depletionTest(total = c.total,
                  marked = c.marked,
                  draws = c.draws,
                  markedDraws = c.markedDraws)

  def enrichmentTest[T](aprioriSets: Seq[Set[T]],
                        targetSet: Set[T]): Seq[(Double, Counts)] = {
    val bg = aprioriSets.flatMap(_.toSeq).toSet
    val bgs = bg.size
    val tgbg = (bg & targetSet)
    val tgbgs = tgbg.size
    aprioriSets.map { ap =>
      val md = (tgbg & ap).size
      val c =
        Counts(total = bgs, marked = ap.size, draws = tgbgs, markedDraws = md)
      val p = enrichmentTest(c)
      (p, c)
    }
  }

  def depletionTest[T](aprioriSets: Seq[Set[T]],
                       targetSet: Set[T]): Seq[(Double, Counts)] = {
    val bg = aprioriSets.flatMap(_.toSeq).toSet
    val bgs = bg.size
    val tgbg = (bg & targetSet)
    val tgbgs = tgbg.size
    aprioriSets.map { ap =>
      val md = (tgbg & ap).size
      val c =
        Counts(total = bgs, marked = ap.size, draws = tgbgs, markedDraws = md)
      val p = depletionTest(c)
      (p, c)
    }
  }

  def readGMT(source: scala.io.Source): Vector[(String, Set[String])] =
    source.getLines.map { line =>
      val splitted = line.split("\\t")
      val name = splitted.head.replaceAll("/", "")
      val rest = splitted.drop(2).toSeq
      name -> rest.toSet
    }.toVector

  def enrichmentTest(
      gmt: scala.io.Source,
      targetSet: Set[String]): Seq[(String, (Double, Counts))] = {
    val gmtc = readGMT(gmt)
    val ps = enrichmentTest(gmtc.map(_._2), targetSet)
    gmtc zip ps map (x => x._1._1 -> x._2)
  }

  def depletionTest(
      gmt: scala.io.Source,
      targetSet: Set[String]): Seq[(String, (Double, Counts))] = {
    val gmtc = readGMT(gmt)
    val ps = depletionTest(gmtc.map(_._2), targetSet)
    gmtc zip ps map (x => x._1._1 -> x._2)
  }

  def enrichmentTest(gmt: java.io.File,
                     targetSet: Set[String]): Seq[(String, (Double, Counts))] =
    fileutils.openSource(gmt)(s => enrichmentTest(s, targetSet))

  def depletionTest(gmt: java.io.File,
                    targetSet: Set[String]): Seq[(String, (Double, Counts))] =
    fileutils.openSource(gmt)(s => depletionTest(s, targetSet))

}
