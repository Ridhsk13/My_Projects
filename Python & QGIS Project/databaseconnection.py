from qgis.core import *
from pyspatialite import dbapi2 as db

conn = db.connect("C:/Users/RIDHS/spatialdb.sqlite")
c = conn.cursor()


c.execute("SELECT p1.name, p2.name, Distance(p1.geom,p2.geom)*11.36 "
                "FROM polygons p1, polygons p2 "
                "WHERE p1.name='H_CentralLibrary_CL' AND p2.name='H_ScienceHall_SH'")

for row in c:
    print('Distance between:',row[0],' & ',row[1], 'is =', row[2],'meter')

#   
#fields = QgsFields()
#fields.append(QgsField("first", QVariant.Int))
#fields.append(QgsField("second", QVariant.String))
#
#writer = QgsVectorFileWriter("c:/users/RIDHS/Desktop/result2.shp", "EPSG4326", fields, QGis.WKBPoint, None, "ESRI Shapefile")
#
#if writer.hasError() != QgsVectorFileWriter.NoError:
#    print("Error when creating shapefile: ",  writer.errorMessage())
#
#fet = QgsFeature()
#
#fet.setGeometry(QgsGeometry.fromPoint(QgsPoint(10,10)))
#fet.setAttributes([1, "text"])
#writer.addFeature(fet)
#
#del writer
#layer = iface.addVectorLayer("c:/users/RIDHS/Desktop/result2.shp", "layer", "ogr")
#if not layer:
#  print("Layer failed to load!")
##for row in c:
# #   print(row)
# #   fields.append(QgsField("first", QVariant.Int))
#
#