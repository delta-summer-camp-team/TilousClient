package com.delta.graphics.controllers

import com.badlogic.gdx.InputAdapter
import com.delta.graphics.geometry.Camera

// camera.position
class CameraController(
    private val camera: Camera
) : InputAdapter() {

    private val lastPosition = camera.getPosition()

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastPosition.set(camera.screenToWorld(screenX, screenY))
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val newPosition = camera.screenToWorld(screenX, screenY)
        val differenceVectors = lastPosition.sub(newPosition)
        camera.updatePosition(differenceVectors)
        return true
    }
}