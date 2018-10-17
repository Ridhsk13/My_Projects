from qgis.core import *
from qgis.utils import *
from qgis.PyQt.QtCore import QVariant
from pyspatialite import dbapi2 as db

conn = db.connect("C:/Users/RIDHS/spatialdb.sqlite")
c = conn.cursor()

c.execute("SELECT name "
                "FROM polygons "
                "WHERE type='parking'")

selected_name = []
for row in c:
    selected_name.append(row[0])
    print(selected_name)

layer=None
for lyr in QgsMapLayerRegistry.instance().mapLayers().values():
    if lyr.name() == "polygons":
        layer = lyr
        break;
             
selected_fid = []
for feature in layer.getFeatures():
    featurename = feature['name'];
    for name in selected_name:
        if  featurename == name:
            selected_fid.append(feature.id())
            print(selected_fid)
            
layer.setSelectedFeatures(selected_fid)
iface.mapCanvas().refresh()
#print(layer.getFe
#fields = QgsFields()
#fields.append(QgsField("name", QVariant.Int))

##writer = QgsVectorFileWriter("my_result.shp", "EPSG4326", fields, QGis.WKBPolygon, None, "ESRI Shapefile")


#writer.addFeature(layer.getFeature
