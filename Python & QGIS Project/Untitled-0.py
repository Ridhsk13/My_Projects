from qgis.core import *

layer = iface.activeLayer()
features = layer.getFeatures()
totalLength = 0
for feature in features:
# retrieve every feature with its geometry and attributes
# fetch geometry
    geom = feature.geometry()
    print("Feature ID: ", feature.id())
    # show some information about the feature
    if geom.wkbType() == QgsWKBTypes.Point:
        x = geom.asPoint()
        print("Point:", x)
    elif geom.wkbType() == QgsWKBTypes.LineString:
        x = geom.asPolyline()
        totalLength += geom.length()
        print('Line:', x, 'points', 'length:', geom.length())
    elif geom.wkbType() == QgsWKBTypes.Polygon:
        x = geom.asPolygon()
        print("Polygon:", x, "Area: ", geom.area())
    else:
        print("Unknown")
    # fetch attributes
    attrs = feature.attributes()
    # attrs is a list. It contains all the attribute values of this feature
    print(attrs)
print("Total length (km):",totalLength*111.32)