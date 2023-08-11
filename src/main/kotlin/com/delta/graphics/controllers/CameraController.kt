package com.delta.graphics.controllers

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3
import com.delta.graphics.geometry.Camera

// camera.position
class CameraController(
    private val camera: Camera
) : InputAdapter() {

    private val lastPosition = Vector3()
    private var positionOnTouch = Pair(0, 0)

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        positionOnTouch = screenX to screenY
        lastPosition.set(camera.screenToWorld(screenX, screenY))
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val newPosition = camera.screenToWorld(screenX, screenY)
        lastPosition.sub(newPosition)
        camera.translate(lastPosition)
        lastPosition.set(camera.screenToWorld(screenX, screenY))
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return positionOnTouch!= screenX to screenY
    }
}